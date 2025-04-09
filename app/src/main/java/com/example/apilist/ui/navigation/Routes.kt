package com.example.apilist.ui.navigation

import kotlinx.serialization.Serializable

sealed class Destinations {
    @Serializable
    object ListScreen: Destinations()
    @Serializable
    object FavScreen: Destinations()
    @Serializable
    object SettingsScreen: Destinations()
}
