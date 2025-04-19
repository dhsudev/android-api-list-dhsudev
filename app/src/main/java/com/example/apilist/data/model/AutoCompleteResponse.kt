package com.example.apilist.data.model

import com.google.gson.annotations.SerializedName

data class AutocompleteResponse(
    @SerializedName("object") val objectType: String,
    val data: List<String>
)