package com.example.apilist.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apilist.data.model.Card
import com.example.apilist.viewmodel.ListApiViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.apilist.ui.utils.getColorFromCard
import com.example.apilist.ui.utils.getManaIconResource
import com.example.apilist.ui.utils.ImageWithCoil
import com.example.apilist.ui.utils.FormatTextWithIcons

@Composable
fun ListScreen() {
    val vm: ListApiViewModel = viewModel()
    val cards = vm.cardList
    val isLoading = vm.isLoading
    val error = vm.errorMessage

    when {
        isLoading && cards.isEmpty() -> {
            // Loading first cards
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        error != null && cards.isEmpty() -> {
            // Something went wrong
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = error, color = Color.Red)
            }
        }

        else -> {
            // Display cards
            CardList(cards, vm)
        }
    }
}

@Composable
fun CardList(cards: List<Card>, vm: ListApiViewModel = viewModel()) {
    Column {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            itemsIndexed(cards) { index, card ->
                if (vm.isListView)
                    CardItemList(card)
                else
                    card.image_uris?.normal?.let { ImageWithCoil(it) }

                // Detect the end of list (infinite scroll)
                if (index == cards.lastIndex && !vm.isLoading) {
                    // Load more
                    LaunchedEffect(Unit) {
                        vm.getMoreCards()
                    }
                }
            }

            // Spinner loading
            if (vm.isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun CardItemList(card: Card) {
    val gradientBackground = getColorFromCard(card.colors)

    val colorsInGradient = listOf(
        Color(0xFFFFFDE7),
        Color(0xFFE3F2FD)
    )
    val averageLuminance = colorsInGradient
        .map { it.luminance() }
        .average()
    val textColor = if (averageLuminance < 0.5f) Color.White else Color.Black

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(gradientBackground, shape = RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row() {
            Column(modifier = Modifier.padding(16.dp)) {
                card.mana_cost?.let { manaCost ->
                    /*Text(
                        text = "Mana Cost: $manaCost",
                        style = MaterialTheme.typography.bodySmall,
                        color = textColor
                    )*/

                    Row(modifier = Modifier.padding(top = 4.dp)) {
                        manaCost.split("}").forEach { symbol ->
                            val cleanSymbol = symbol.trim().removePrefix("{").removeSuffix("}")
                            if (cleanSymbol.isNotEmpty()) {
                                Image(
                                    painter = painterResource(id = getManaIconResource(cleanSymbol)),
                                    contentDescription = "Mana Symbol",
                                    modifier = Modifier
                                        .padding(end = 4.dp)
                                        .size(20.dp),
                                )
                            }
                        }
                        Text(
                            text = card.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = textColor
                        )
                    }
                }
                card.type_line?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = textColor
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                //FormatTextWithIcons(card.oracle_text ?: "")
                /*card.oracle_text?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = textColor
                    )
                }*/


            }
            //card.image_uris?.art_crop?.let { ImageWithCoil(it, modifier = Modifier.width(100.dp)) }
        }
    }
}

