package com.example.apilist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.apilist.viewmodel.ListApiViewModel

@Composable
fun FavScreen(vm : ListApiViewModel,navigateToDetail: (String) -> Unit) {
    val cards = vm.cardList
    val isLoading = vm.isLoading
    val error = vm.errorMessage

    LaunchedEffect(Unit) {
        vm.fetchFavorites()
    }

    when {
        isLoading && vm.isFavLoaded -> {
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
            if(!vm.isFavLoaded)
                vm.fetchFavorites()
            if(cards.isEmpty()) {
                Column(
                    Modifier.fillMaxSize().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                )
                {
                    Text("No favorites yet")
                }
            }
            else
                CardList(cards, vm, true, navigateToDetail)
        }
    }
}