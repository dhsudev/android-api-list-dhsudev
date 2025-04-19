package com.example.apilist.ui.utils.reusableitems

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.apilist.data.model.Card
import com.example.apilist.data.model.local.UserSettings
import com.example.apilist.ui.utils.FormatTextWithIcons
import com.example.apilist.ui.utils.getBackgroundGradient
import com.example.apilist.ui.utils.getColorFromCard
import com.example.apilist.ui.utils.getManaIconResource

@Composable
fun CardItemList(card: Card, settings: UserSettings, modifier: Modifier) {
    val colorsInCard = getColorFromCard(card.colors, settings.isDarkTheme)
    val gradientBackground = getBackgroundGradient(colorsInCard)

    val averageLuminance = colorsInCard
        .map { it.luminance() }
        .average()
    val textColor = if (averageLuminance < 0.5f) Color.White else Color.Black

    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(gradientBackground, shape = RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row() {
            Column(modifier = Modifier.padding(16.dp)) {
                card.mana_cost?.let { manaCost ->
                    /*
                    // DEBUG TEXT
                    Text(
                        text = "Mana Cost: $manaCost",
                        style = MaterialTheme.typography.bodySmall,
                        color = textColor
                    )
                    */

                    Row(modifier = Modifier.padding(top = 4.dp)) {
                        if (settings.showManaCost) {
                            manaCost.split("}").forEach { symbol ->
                                val cleanSymbol = symbol.trim().removePrefix("{").removeSuffix("}")
                                if (cleanSymbol.isNotEmpty()) {
                                    Image(
                                        painter = painterResource(
                                            id = getManaIconResource(
                                                cleanSymbol
                                            )
                                        ),
                                        contentDescription = "Mana Symbol",
                                        modifier = Modifier
                                            .padding(end = 4.dp)
                                            .size(20.dp),
                                    )
                                }
                            }
                        }
                        Text(
                            text = card.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = textColor
                        )
                    }
                }
                if (settings.showTypeLine) {
                    card.type_line?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = textColor
                        )
                    }
                }
                if (settings.showOracleText) {
                    Spacer(modifier = Modifier.height(8.dp))
                    FormatTextWithIcons(card.oracle_text ?: "", textColor)
                }
                if (settings.showPowerToughness && card.power != null && card.toughness != null) {
                    Text(
                        text = "${card.power}/${card.toughness}",
                        style = MaterialTheme.typography.bodySmall,
                        color = textColor,
                        modifier = Modifier.padding(top = 8.dp).align(Alignment.End)

                    )
                }
            }
        }
    }
}