package com.example.apilist.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import com.example.apilist.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

fun getColorFromCard(colors: List<String>?, isDarkMode: Boolean): List<Color> {
    val colorMap = mapOf(
        "W" to Color(0xFFFFFDE7),
        "U" to Color(0xFFE3F2FD),
        "B" to Color(0xFFAFAEAE),
        "R" to Color(0xFFFFEBEE),
        "G" to Color(0xFFE8F5E9)
    )

    if (colors.isNullOrEmpty()) {
        return if (isDarkMode) {
            listOf(Color.DarkGray)
        } else {
            listOf(Color.LightGray)
        }
    }

    val colorsInCard = colors.mapNotNull { colorMap[it] }

    return if (isDarkMode) {
        colorsInCard.map { color ->
            color.copy(alpha = 0.8f).copy(red = color.red * 0.8f, green = color.green * 0.8f, blue = color.blue * 0.8f)
        }
    } else {
        colorsInCard
    }
}

fun getBackgroundGradient(colors: List<Color>): Brush {
    return if (colors.size == 1) {
        Brush.linearGradient(listOf(colors.first(), colors.first()))
    } else {
        Brush.linearGradient(colors)
    }
}




fun getManaIconResource(manaSymbol: String): Int {
    return when (manaSymbol.uppercase()) {
        "W" -> R.drawable.mana_w
        "U" -> R.drawable.mana_u
        "B" -> R.drawable.mana_b
        "R" -> R.drawable.mana_r
        "G" -> R.drawable.mana_g
        "C" -> R.drawable.mana_c
        "X" -> R.drawable.mana_x
        "Y" -> R.drawable.mana_y
        "Z" -> R.drawable.mana_z
        "S" -> R.drawable.mana_s
        "T" -> R.drawable.mana_t
        "Q" -> R.drawable.mana_q
        "W/P" -> R.drawable.mana_wp
        "U/P" -> R.drawable.mana_up
        "B/P" -> R.drawable.mana_bp
        "R/P" -> R.drawable.mana_rp
        "G/P" -> R.drawable.mana_gp
        "W/U" -> R.drawable.mana_wu
        "W/B" -> R.drawable.mana_wb
        "U/B" -> R.drawable.mana_ub
        "U/R" -> R.drawable.mana_ur
        "B/R" -> R.drawable.mana_br
        "B/G" -> R.drawable.mana_bg
        "R/G" -> R.drawable.mana_rg
        "R/W" -> R.drawable.mana_rw
        "G/W" -> R.drawable.mana_gw
        "G/U" -> R.drawable.mana_gu
        "2/W" -> R.drawable.mana_2w
        "2/U" -> R.drawable.mana_2u
        "2/B" -> R.drawable.mana_2b
        "2/R" -> R.drawable.mana_2r
        "2/G" -> R.drawable.mana_2g
        "CHAOS" -> R.drawable.mana_chaos
        "1" -> R.drawable.mana_1
        "2" -> R.drawable.mana_2
        "3" -> R.drawable.mana_3
        "4" -> R.drawable.mana_4
        "5" -> R.drawable.mana_5
        "6" -> R.drawable.mana_6
        "7" -> R.drawable.mana_7
        "8" -> R.drawable.mana_8
        "9" -> R.drawable.mana_9
        "10" -> R.drawable.mana_10
        "11" -> R.drawable.mana_11
        "12" -> R.drawable.mana_12
        "13" -> R.drawable.mana_13
        "14" -> R.drawable.mana_14
        "15" -> R.drawable.mana_15
        "16" -> R.drawable.mana_16
        "17" -> R.drawable.mana_17
        "18" -> R.drawable.mana_18
        "19" -> R.drawable.mana_19
        "20" -> R.drawable.mana_20
        else -> R.drawable.ic_launcher_foreground
    }
}

@Composable
fun FormatTextWithIcons(str: String, textColor: Color) {
    val pattern = Regex("\\{(.*?)\\}")
    val lines = str.split("\n")

    Column {
        lines.forEachIndexed { lineIndex, line ->
            val inlineContentMap = mutableMapOf<String, InlineTextContent>()
            val annotatedString = buildAnnotatedString {
                var lastIndex = 0
                pattern.findAll(line).forEachIndexed { index, matchResult ->
                    val start = matchResult.range.first
                    if (start > lastIndex) {
                        append(line.substring(lastIndex, start))
                    }

                    val symbol = matchResult.groupValues[1]
                    val tag = "inlineContent-$index"
                    appendInlineContent(tag, "[icon]")

                    inlineContentMap[tag] = InlineTextContent(
                        Placeholder(
                            width = 22.sp,
                            height = 22.sp,
                            placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                        )
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(getManaIconResource(symbol)),
                            contentDescription = symbol,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    lastIndex = matchResult.range.last + 1
                }

                if (lastIndex < line.length) {
                    append(line.substring(lastIndex))
                }
            }

            Text(
                text = annotatedString,
                inlineContent = inlineContentMap,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor
            )
            if(lineIndex < lines.size - 1)
                Spacer(Modifier.size(5.dp))
        }

    }
}
