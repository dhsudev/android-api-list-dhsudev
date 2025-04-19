package com.example.apilist.data.model

data class Card(
    val id: String,
    val name: String,
    val mana_cost: String?,
    val type_line: String?,
    val oracle_text: String?,
    val colors: List<String>?,
    val image_uris: ImageUris?
)