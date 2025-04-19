package com.example.apilist.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apilist.data.model.Card
import com.example.apilist.data.model.local.UserSettings
import com.example.apilist.ui.utils.reusableitems.CardItemList
import com.example.apilist.ui.utils.reusableitems.ImageWithCoil
import com.example.apilist.viewmodel.ListApiViewModel

@Composable
fun ListScreen(vm: ListApiViewModel, navigateToDetail: (String) -> Unit) {
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
            CardList(cards, vm, navigateToDetail)
        }
    }
}

@Composable
fun CardList(cards: List<Card>, vm: ListApiViewModel = viewModel(), navigateToDetail : (String) -> Unit) {
    val settings: UserSettings = vm.userSettings!!
    Column {
        if (settings.isTextView) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                itemsIndexed(cards) { index, card ->
                    CardItemList(card, settings, Modifier.clickable { navigateToDetail(card.id) })

                    if (index == cards.lastIndex && !vm.isLoading) {
                        LaunchedEffect(Unit) {
                            vm.getMoreCards()
                        }
                    }
                }

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
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(cards) { index, card ->
                    card.image_uris?.normal?.let {
                        ImageWithCoil(it, Modifier.clickable { navigateToDetail(card.id) })
                    }

                    if (index == cards.lastIndex && !vm.isLoading) {
                        LaunchedEffect(Unit) {
                            vm.getMoreCards()
                        }
                    }
                }

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
}


