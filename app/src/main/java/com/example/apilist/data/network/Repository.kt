package com.example.apilist.data.network

import android.util.Log
import com.example.apilist.CardApplication
import com.example.apilist.data.database.AppDatabase
import com.example.apilist.data.model.Card
import com.example.apilist.data.model.ScryfallResponse
import com.example.apilist.data.model.local.FavoriteCard
import com.example.apilist.data.model.local.UserSettings

class Repository(){
    private val database: AppDatabase = CardApplication.database
    private val userSettingsDao = database.settingsDao()
    private val favoriteCardDao = database.favoriteCardDao()
    private val api = ApiInterface.create()
    // ############## NETWORK

    // SCRYFALL (cards)
    suspend fun getAllCards() = api.getAllCards()
    suspend fun getMoreCards(urlNext: String): ScryfallResponse {
        return api.getMoreCards(urlNext)
    }

    suspend fun getCardById(cardId: String) = api.getCardById(cardId)
    suspend fun getAutocompleteSuggestions(query: String): List<String> {
        return try {
            val response = api.autocomplete(query)
            response.data
        } catch (e: Exception) {
            emptyList()
        }
    }
    suspend fun getCardByExactName(name: String): Card? {
        return try {
            api.getCardByName(name)
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching card by name", e)
            null
        }
    }

   /* It was suposed to be for filters
   suspend fun buildSearchQuery(
        name: String?,
        type: String?,
        color: String?,
        power: Int?
    ): ScryfallResponse {
        val queryParts = mutableListOf<String>()

        name?.let { queryParts.add("name:$it") }
        type?.let { queryParts.add("type:$it") }
        color?.let { queryParts.add("color:$it") }
        power?.let { queryParts.add("power:$it") }

        return api.searchCards(queryParts.joinToString("+"))
    }*/

    // ############## LOCAL STORAGE

    // USER SETTINGS
    suspend fun saveUserSettings(settings: UserSettings) {
        userSettingsDao.saveSettings(settings)
    }

    suspend fun getUserSettings(): UserSettings? {
        return userSettingsDao.getSettings()
    }

    // USER FAVORITES
    suspend fun clearFavorites() {
        favoriteCardDao.clearFavorites()
    }

    suspend fun addFavorite(card : FavoriteCard) {
        favoriteCardDao.insertFavorite(card)
    }

    suspend fun removeFavorite(card : FavoriteCard){
        favoriteCardDao.deleteFavorite(card)
    }

    suspend fun isFavorite(id: String): Boolean {
        return favoriteCardDao.isFavorite(id)
    }

    suspend fun getFavorites(): List<FavoriteCard> {
        return favoriteCardDao.getAllFavorites()
    }
}
