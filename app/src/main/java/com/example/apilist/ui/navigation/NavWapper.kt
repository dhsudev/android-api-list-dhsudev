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
import com.example.apilist.ui.navigation.Destinations.FavScreen
import com.example.apilist.ui.navigation.Destinations.SettingsScreen


@Composable
fun NavWrapper(navController: NavHostController, modifier: Modifier) {
    NavHost(navController, ListScreen, modifier = modifier) {
        composable<Destinations.ListScreen> {
            ListScreen()
        }
        composable<Destinations.FavScreen> {
            FavScreen()
        }
        composable<Destinations.SettingsScreen> {
            SettingsScreen()
        }
    }
}

