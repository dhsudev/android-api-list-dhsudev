package com.example.apilist.viewmodel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apilist.data.model.Card
import com.example.apilist.data.model.local.FavoriteCard
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

    var isFavorite by mutableStateOf(false)
        private set

    private var nextPage by mutableStateOf<String?>(null)

    var isFavLoaded by mutableStateOf(false)
        private set

    init {
        getUserSettings()
        fetchAllCards()
        Log.d("ViewModel", "ViewModel initialized")
    }

    public fun fetchAllCards() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val response = repository.getAllCards()
                Log.d("API", "First page fetched, recived cards: ${response.data.size}")
                cardList = response.data
                nextPage = response.next_page
                Log.d("API", "Next page: $nextPage")
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
                Log.e("API", "Error fetching cards: ${e.message}")
            } finally {
                isLoading = false
                isFavLoaded = false
            }
        }
    }

    fun getMoreCards() {
        val next = nextPage
        if (next == null || isLoading) {
            Log.d("API", "There is no more data to load or is already in progress")
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                Log.d("API", "Fetching more cards, page: $next")
                val response = repository.getMoreCards(next)
                cardList = cardList + response.data
                nextPage = response.next_page
                Log.d("API", "Added cards: ${response.data.size}")
                Log.d("API", "Next page: $nextPage")
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
                Log.e("API", "Error fetching more cards: ${e.message}")
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
                isFavorite = repository.isFavorite(cardId)
                Log.d("API", "Card with ID $cardId fetched successfully")
                Log.d("API", "Is favorite?: $isFavorite")
            } catch (e: Exception) {
                selectedCard = null
                errorMessage = "Error: ${e.message}"
                Log.e("API", "Error fetching card with ID $cardId: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun getUserSettings() {
        viewModelScope.launch {
            try {
                val settings = repository.getUserSettings()
                Log.d("ViewModel", "User settings loaded successfully")
                Log.d("ViewModel", "User settings: $settings")
                settings?.let {
                    userSettings = it
                }
                if (userSettings == null) {
                    Log.d("ViewModel", "User settings are null, saving default values")
                    saveUserSettings(UserSettings())
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Error loading user settings", e)
            }
        }
    }

    fun saveUserSettings(settings: UserSettings) {
        viewModelScope.launch {
            try {
                repository.saveUserSettings(settings)
                userSettings = settings
            } catch (e: Exception) {
                Log.e("ViewModel", "Error saving user settings", e)
            }
        }
    }

    fun clearFavorites() {
        viewModelScope.launch {
            try {
                repository.clearFavorites()
                Log.d("ViewModel", "Favourites cleared successfully")
            } catch (e: Exception) {
                Log.e("ViewModel", "Error while clearing favorites", e)
            }
        }
    }

    fun toggleFavorite(card: Card) {
        viewModelScope.launch {
            try {
                if (isFavorite) {
                    repository.removeFavorite(card.toFavoriteCard())
                    isFavorite = false
                    Log.d("ViewModel", "Card with ID ${card.id} removed from favorites")
                } else {
                    repository.addFavorite(card.toFavoriteCard())
                    isFavorite = true
                    Log.d("ViewModel", "Card with ID ${card.id} added to favorites")
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Error updating favorite status", e)
            }
        }
    }

    fun fetchFavorites() {
        viewModelScope.launch {
            errorMessage = null
            try {
                val favCards = repository.getFavorites()
                Log.d("API", "Favorites fetched, recived cards: ${favCards}")
                cardList = favCards.map { it.toCard() }
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
                Log.e("API", "Error fetching cards: ${e.message}")
            } finally {
                isFavLoaded = true
            }
        }
    }
}
