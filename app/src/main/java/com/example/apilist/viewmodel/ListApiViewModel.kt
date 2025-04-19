package com.example.apilist.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apilist.data.network.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.apilist.data.model.Card
import com.example.apilist.data.model.local.UserSettings

class ListApiViewModel : ViewModel() {

    private val repository = Repository()
    private val nextPage = MutableLiveData<String?>()

    var cardList by mutableStateOf<List<Card>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isListView by mutableStateOf(true)
        private set

    init {
        fetchAllCards()
    }

    fun fetchAllCards() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val response = repository.getAllCards()
                Log.d("API", "Primera carga, cartas recibidas: ${response.data.size}")
                cardList = response.data
                nextPage.value = response.next_page
                Log.d("API", "Siguiente página: ${response.next_page}")
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
                Log.e("API", "Error al cargar las cartas: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun getMoreCards() {
        val next = nextPage.value
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
                Log.d("API", "Cartas añadidas: ${response.data.size}")
                nextPage.value = response.next_page
                Log.d("API", "Nueva siguiente página: ${response.next_page}")
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
                Log.e("API", "Error al cargar más cartas: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    var userSettings = mutableStateOf(UserSettings())

    // Get user settings from the database
    fun getUserSettings() {
        viewModelScope.launch {
            try {
                val settings = repository.getUserSettings()
                settings?.let {
                    userSettings.value = it
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Error getting user settings", e)
            }
        }
    }

    // Save user settings to the database
    fun saveUserSettings(settings: UserSettings) {
        viewModelScope.launch {
            try {
                repository.saveUserSettings(settings)
                Log.d("ViewModel", "User settings saved successfully")
            } catch (e: Exception) {
                Log.e("ViewModel", "Error saving user settings", e)
            }
        }
    }

    // Clear favorites
    fun clearFavorites() {
        viewModelScope.launch {
            try {
                repository.clearFavorites()
                Log.d("ViewModel", "Favorites cleared successfully")
            } catch (e: Exception) {
                Log.e("ViewModel", "Error clearing favorites", e)
            }
        }
    }
}
