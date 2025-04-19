package com.example.apilist.viewmodel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apilist.data.model.Card
import com.example.apilist.data.model.local.UserSettings
import com.example.apilist.data.network.Repository
import kotlinx.coroutines.launch

class ListApiViewModel : ViewModel() {

    private val repository = Repository()

    var cardList by mutableStateOf<List<Card>>(emptyList())
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var userSettings by mutableStateOf<UserSettings?>(null)
        private set

    var selectedCard by mutableStateOf<Card?>(null)
        private set

    private var nextPage by mutableStateOf<String?>(null)

    init {
        fetchAllCards()
        getUserSettings()
    }

    private fun fetchAllCards() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val response = repository.getAllCards()
                Log.d("API", "Primera carga, cartas recibidas: ${response.data.size}")
                cardList = response.data
                nextPage = response.next_page
                Log.d("API", "Siguiente página: $nextPage")
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
                Log.e("API", "Error al cargar las cartas: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun getMoreCards() {
        val next = nextPage
        if (next == null || isLoading) {
            Log.d("API", "No hay más páginas o ya está cargando")
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                Log.d("API", "Cargando más cartas desde: $next")
                val response = repository.getMoreCards(next)
                cardList = cardList + response.data
                nextPage = response.next_page
                Log.d("API", "Cartas añadidas: ${response.data.size}")
                Log.d("API", "Nueva siguiente página: $nextPage")
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
                Log.e("API", "Error al cargar más cartas: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun getCardById(cardId: String) {
        isLoading = true
        viewModelScope.launch {
            try {
                val card = repository.getCardById(cardId)
                selectedCard = card
            } catch (e: Exception) {
                selectedCard = null
                errorMessage = "Error: ${e.message}"
                Log.e("API", "Error al obtener la carta: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun getUserSettings() {
        viewModelScope.launch {
            try {
                val settings = repository.getUserSettings()
                settings?.let {
                    userSettings = it
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Error obteniendo configuraciones del usuario", e)
            }
        }
    }

    fun saveUserSettings(settings: UserSettings) {
        viewModelScope.launch {
            try {
                repository.saveUserSettings(settings)
                userSettings = settings
            } catch (e: Exception) {
                Log.e("ViewModel", "Error guardando configuraciones del usuario", e)
            }
        }
    }

    fun clearFavorites() {
        viewModelScope.launch {
            try {
                repository.clearFavorites()
                Log.d("ViewModel", "Favoritos borrados exitosamente")
            } catch (e: Exception) {
                Log.e("ViewModel", "Error borrando favoritos", e)
            }
        }
    }
}
