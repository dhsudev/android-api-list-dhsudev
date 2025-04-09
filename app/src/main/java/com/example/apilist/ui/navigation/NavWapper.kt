package com.example.apilist.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.apilist.ui.navigation.Destinations.ListScreen
import com.example.apilist.ui.navigation.Destinations.FavScreen
import com.example.apilist.ui.navigation.Destinations.SettingsScreen


@Composable
fun NavWrapper(navController: NavHostController) {
    NavHost(navController, ListScreen) {
        composable<ListScreen> {

        }
        //TODO: resta de rutes
        composable<FavScreen> {
            //TODO: càrrega del composable amb paràmetres (si en té)
        }
        composable<SettingsScreen> {
            //TODO: càrrega del composable amb paràmetres (si en té)
        }
    }
}

