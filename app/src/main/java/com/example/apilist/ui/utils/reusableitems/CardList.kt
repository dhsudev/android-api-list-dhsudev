package com.example.apilist.ui.utils.reusableitems

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apilist.data.model.Card
import com.example.apilist.data.model.local.UserSettings
import com.example.apilist.viewmodel.ListApiViewModel

@Composable
fun CardList(cards: List<Card>, vm: ListApiViewModel = viewModel(), isFavScreen : Boolean, navigateToDetail : (String) -> Unit) {
    val settings: UserSettings = vm.userSettings!!
    Column {
        val textTitle = if(isFavScreen) "Favorites" else "All Cards"

        Text(textTitle, style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(10.dp).align(Alignment.CenterHorizontally))
        if(!isFavScreen)
            MagicSearchBar(vm, navigateToDetail)


        if (settings.isTextView) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                itemsIndexed(cards) { index, card ->
                    CardItemList(card, settings, Modifier.clickable { navigateToDetail(card.id) })

                    if (index == cards.lastIndex && !vm.isLoading && !isFavScreen) {
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

                    if (index == cards.lastIndex && !vm.isLoading && !isFavScreen) {
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
