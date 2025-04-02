package com.example.apilist.data.network

import com.example.apilist.data.model.SearchData
import retrofit2.Response

class Repository {
    val api = ApiInterface.create()
    suspend fun getAllCards() = api.getAllCards()
    suspend fun getCardById(cardId : String) = api.getCardById(cardId)
    suspend fun autocomplete(name : String) = api.autocomplete(name)
    suspend fun buildSearchQuery(name: String?, type: String?, color: String?, power: Int?): Response<SearchData> {
        val queryParts = mutableListOf<String>()

        name?.let { queryParts.add("name:$it") }
        type?.let { queryParts.add("type:$it") }
        color?.let { queryParts.add("color:$it") }
        power?.let { queryParts.add("power:$it") }

        return api.searchCards(queryParts.joinToString("+"))
    }

}
