package com.example.apilist.data.model

import com.example.apilist.data.model.local.FavoriteCard
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
){
    fun toFavoriteCard(): FavoriteCard {
        return FavoriteCard(
            id = id,
            name = name,
            mana_cost = mana_cost,
            type_line = type_line,
            oracle_text = oracle_text,
            colors = colors,
            image_uris = image_uris,
            power = power,
            toughness = toughness
        )
    }
}