package com.example.apilist.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.apilist.ui.utils.FormatTextWithIcons
import com.example.apilist.ui.utils.getBackgroundGradient
import com.example.apilist.ui.utils.getColorFromCard
import com.example.apilist.ui.utils.getManaIconResource
import com.example.apilist.ui.utils.reusableitems.ImageWithCoil
import com.example.apilist.viewmodel.ListApiViewModel
import androidx.compose.foundation.rememberScrollState


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    cardId: String,
    vm: ListApiViewModel,
    navigateBack: () -> Unit
) {
    val selectedCard = vm.selectedCard
    val isLoading = vm.isLoading
    val errorMessage = vm.errorMessage
    val context = LocalContext.current

    LaunchedEffect(cardId) {
        vm.getCardById(cardId)
    }

    // UI de la pantalla
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details of the card") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                selectedCard?.let { card ->
                    val colorsInCard = getColorFromCard(card.colors, vm.userSettings?.isDarkTheme ?: false)
                    val gradientBackground = getBackgroundGradient(colorsInCard)

                    val averageLuminance = colorsInCard
                        .map { it.luminance() }
                        .average()
                    val textColor = if (averageLuminance < 0.5f) Color.White else Color.Black
                    val scrollState = rememberScrollState()
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .background(gradientBackground, shape = RoundedCornerShape(16.dp))
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Imagen de la carta
                        card.image_uris?.art_crop?.let {
                            ImageWithCoil(card.image_uris.art_crop, Modifier)
                        }

                        // Información de la carta
                        card.name.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.headlineLarge,
                                color = textColor
                            )
                        }

                        // Mana Cost
                        card.mana_cost?.let { manaCost ->
                            Row(modifier = Modifier.padding(top = 4.dp)) {
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
                        }

                        card.type_line?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyLarge,
                                color = textColor
                            )
                        }

                        card.oracle_text?.let { it1 -> FormatTextWithIcons(it1, textColor) }


                        if (card.power != null && card.toughness != null) {
                            Text(
                                text = "${card.power}/${card.toughness}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = textColor,
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .align(Alignment.End)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            onClick = {
                                vm.toggleFavorite(card)
                                val message = if (!vm.isFavorite) {
                                    "Card added to favorites"
                                } else {
                                    "Card removed from favorites"
                                }
                                Toast.makeText(context, message, LENGTH_SHORT).show()
                            }
                        ) {
                            val icon = if (vm.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                            val tint = if (vm.isFavorite) Color.Red else Color.Gray
                            Icon(icon, contentDescription = "Favorite", tint = tint)
                        }
                    }
                } ?: errorMessage?.let{
                    Text("Error: $it", color = Color.Red)
                }
            }
        }
    }
}


