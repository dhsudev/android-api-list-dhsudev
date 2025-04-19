package com.example.apilist.data.network

import com.example.apilist.data.model.ScryfallResponse
import retrofit2.Response

class Repository {
    private val api = ApiInterface.create()
    suspend fun getAllCards() = api.getAllCards()
    suspend fun getMoreCards(urlNext : String) : ScryfallResponse{
        return api.getMoreCards(urlNext)
    }
    suspend fun getCardById(cardId : String) = api.getCardById(cardId)
    suspend fun autocomplete(name : String) = api.autocomplete(name)
    suspend fun buildSearchQuery(name: String?, type: String?, color: String?, power: Int?): ScryfallResponse {
        val queryParts = mutableListOf<String>()

        name?.let { queryParts.add("name:$it") }
        type?.let { queryParts.add("type:$it") }
        color?.let { queryParts.add("color:$it") }
        power?.let { queryParts.add("power:$it") }

        return api.searchCards(queryParts.joinToString("+"))
    }


}
