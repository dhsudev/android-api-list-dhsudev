package com.example.apilist.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.apilist.ui.utils.reusableitems.CardList
import com.example.apilist.viewmodel.ListApiViewModel

@Composable
fun ListScreen(vm: ListApiViewModel, navigateToDetail: (String) -> Unit) {
    val cards = vm.cardList
    val isLoading = vm.isLoading
    val error = vm.errorMessage

    LaunchedEffect(Unit) {
        if(cards.isEmpty() || vm.isFavLoaded)
            vm.fetchAllCards()
    }
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
            CardList(cards, vm, false, navigateToDetail)
        }
    }
}

