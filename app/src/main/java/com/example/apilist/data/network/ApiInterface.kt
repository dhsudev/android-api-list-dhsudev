package com.example.apilist.data.network


import com.example.apilist.data.model.SearchData
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface ApiInterface {
    @GET("cards/search")
    suspend fun searchCards(@Query("q") query: String): Response<SearchData>

    @GET("cards/{id}")
    suspend fun getCardById(@Path("id") cardId: String): Response<SearchData>

    @GET("cards/search?q=game:paper")
    suspend fun getAllCards(): Response<SearchData>

    @GET("cards/autocomplete")
    suspend fun autocomplete(@Query("q") query: String) : Response<SearchData>

    companion object {
        val BASE_URL = "https://api.scryfall.com/"
        fun create(): ApiInterface {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                GsonConverterFactory.create()
            ).client(client).build()
            return retrofit.create(ApiInterface::class.java)
        }
    }


    /*companion object {
        const val BASE_URL = "https://api.scryfall.com/"
        fun create(): ApiInterface {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                GsonConverterFactory.create()
            ).client(client).build()
            return retrofit.create(ApiInterface::class.java)
        }
    }*/
}
