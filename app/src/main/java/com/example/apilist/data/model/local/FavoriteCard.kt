package com.example.apilist.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.apilist.data.model.Card
import com.example.apilist.data.model.ImageUris

@Entity(tableName = "favorite_cards")
data class FavoriteCard(
    @PrimaryKey val id: String,
    val name: String,
    val mana_cost: String?,
    val type_line: String?,
    val oracle_text: String?,
    val colors: List<String>?,
    val image_uris: ImageUris?,
    val power: String?,
    val toughness: String?
) {
    fun toCard(): Card {
        return Card(
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
