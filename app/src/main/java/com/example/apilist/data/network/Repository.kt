package com.example.apilist.data.network

import com.example.apilist.CardApplication
import com.example.apilist.data.database.AppDatabase
import com.example.apilist.data.database.FavoriteCardDao
import com.example.apilist.data.database.SettingsDao
import com.example.apilist.data.model.ScryfallResponse
import com.example.apilist.data.model.local.UserSettings
import retrofit2.Response

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
    suspend fun autocomplete(name: String) = api.autocomplete(name)
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
    }

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


}
