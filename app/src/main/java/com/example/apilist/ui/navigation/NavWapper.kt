package com.example.apilist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.apilist.ui.screens.FavScreen
import com.example.apilist.ui.screens.ListScreen
import com.example.apilist.ui.screens.SettingsScreen
import com.example.apilist.ui.navigation.Destinations.ListScreen
import com.example.apilist.ui.screens.DetailScreen
import com.example.apilist.viewmodel.ListApiViewModel


@Composable
fun NavWrapper(navController: NavHostController, modifier: Modifier, vm: ListApiViewModel) {
    NavHost(navController, ListScreen, modifier = modifier) {
        composable<Destinations.ListScreen> {
            ListScreen(vm, navigateToDetail = { cardId ->
                navController.navigate("detail_screen/$cardId")
            })
        }
        composable<Destinations.FavScreen> {
            FavScreen(vm)
        }
        composable<Destinations.SettingsScreen> {
            SettingsScreen(vm)
        }
        composable("detail_screen/{cardId}") { backStackEntry ->
            val cardId = backStackEntry.arguments?.getString("cardId")
            cardId?.let {
                DetailScreen(cardId = it, vm = vm, navigateBack = { navController.popBackStack() })
            }
        }
    }
}

