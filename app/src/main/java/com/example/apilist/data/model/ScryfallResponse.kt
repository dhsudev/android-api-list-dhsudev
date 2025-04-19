package com.example.apilist.data.model

import com.google.gson.annotations.SerializedName

data class ScryfallResponse(
    @SerializedName("object") val objectType: String,
    val total_cards: Int,
    val has_more: Boolean,
    val next_page: String?,
    val data: List<Card>
)
