package com.example.apilist.data.network

class Repository {
    val apiInterface = ApiInterface.create()
    suspend fun getAllCharacters() = apiInterface.getData()

}
