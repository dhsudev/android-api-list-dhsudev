package com.example.apilist.data.network


import android.util.Log
import com.example.apilist.data.model.AutocompleteResponse
import com.example.apilist.data.model.Card
import com.example.apilist.data.model.ScryfallResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiInterface {
    @GET("cards/search")
    suspend fun searchCards(@Query("q") query: String): ScryfallResponse

    @GET("cards/{id}")
    suspend fun getCardById(@Path("id") cardId: String): Card

    @GET("cards/search")
    suspend fun getAllCards(@Query("q") query: String = "legal:commander"): ScryfallResponse

    @GET("cards/autocomplete")
    suspend fun autocomplete(@Query("q") query: String): AutocompleteResponse
    @GET("cards/named")
    suspend fun getCardByName(@Query("exact") name: String): Card

    @GET
    suspend fun getMoreCards(@Url nextPageUrl: String): ScryfallResponse

    companion object {
        const val BASE_URL = "https://api.scryfall.com/"
        fun create(): ApiInterface {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }

}
