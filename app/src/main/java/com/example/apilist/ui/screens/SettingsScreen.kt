package com.example.apilist.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.apilist.viewmodel.ListApiViewModel
import com.example.apilist.data.model.local.UserSettings
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.Nightlight
import androidx.compose.material.icons.rounded.NightsStay


@Composable
fun SettingsScreen(vm: ListApiViewModel) {
    val context = LocalContext.current
    val settings = vm.userSettings

    var tempSettings by remember { mutableStateOf(settings ?: UserSettings()) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Settings", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(16.dp))

        // Theme toggle
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Rounded.LightMode, contentDescription = "Dark Mode")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = tempSettings.isDarkTheme,
                onCheckedChange = {
                    tempSettings = tempSettings.copy(isDarkTheme = it)
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Rounded.Nightlight, contentDescription = "Light Mode")
        }

        Spacer(Modifier.height(16.dp))

        // View type switch
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Grid View")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = tempSettings.isTextView,
                onCheckedChange = {
                    tempSettings = tempSettings.copy(isTextView = it)
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Text View")
        }

        Spacer(Modifier.height(8.dp))

        // Conditional: show info options only in text mode
        if (tempSettings.isTextView) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = tempSettings.showManaCost,
                        onCheckedChange = {
                            tempSettings = tempSettings.copy(showManaCost = it)
                        }
                    )
                    Text("Show Mana Cost")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = tempSettings.showTypeLine,
                        onCheckedChange = {
                            tempSettings = tempSettings.copy(showTypeLine = it)
                        }
                    )
                    Text("Show Type Line")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = tempSettings.showOracleText,
                        onCheckedChange = {
                            tempSettings = tempSettings.copy(showOracleText = it)
                        }
                    )
                    Text("Show Oracle Text")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = tempSettings.showPowerToughness,
                        onCheckedChange = {
                            tempSettings = tempSettings.copy(showPowerToughness = it)
                        }
                    )
                    Text("Show Power/Toughness")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            vm.saveUserSettings(tempSettings)
            Log.d("SettingsScreen", "Settings saved: $tempSettings")
        }) {
            Text("Save Settings")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            vm.clearFavorites()
            Log.d("SettingsScreen", "Favorites cleared")
        }) {
            Text("Clear Favorites")
        }
    }
}
