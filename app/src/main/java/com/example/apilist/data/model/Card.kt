package com.example.apilist.data.model

import com.google.gson.annotations.SerializedName

data class Card(
    val id: String,
    val name: String,
    val mana_cost: String?,
    val type_line: String?,
    val oracle_text: String?,
    @SerializedName("color_identity")
    val colors: List<String>?,
    val image_uris: ImageUris?,
    val power : String?,
    val toughness : String?
)