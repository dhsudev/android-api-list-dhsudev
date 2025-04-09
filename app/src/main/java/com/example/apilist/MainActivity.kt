package com.example.apilist

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract.Contacts.Data
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.apilist.data.network.ApiInterface
import com.example.apilist.data.network.Repository
import com.example.apilist.ui.navigation.Destinations
import com.example.apilist.ui.navigation.NavWrapper
import com.example.apilist.ui.navigation.NavigationItem
import com.example.apilist.ui.theme.APIListTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.Int

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getCardsTest()
        enableEdgeToEdge()
        setContent {
            APIListTheme {
                MyApp()
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun MyApp() {
        var selectedItem: Int by remember { mutableIntStateOf(0) }
        val items = listOf(
            NavigationItem("List", Icons.Default.Menu, Destinations.ListScreen, 0),
            NavigationItem("Favourites", Icons.Default.Favorite, Destinations.FavScreen, 1),
            NavigationItem("Settings", Icons.Default.Settings, Destinations.SettingsScreen, 2)
        )
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
                NavigationBar {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = item.index == selectedItem,
                            label = { Text(item.label) },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label
                                )
                            },
                            onClick = {
                                selectedItem = index
                                navController.navigate(item.route)
                            }
                        )
                    }
                }
            }, modifier = Modifier.fillMaxSize()
        ) {
            NavWrapper(navController)
        }

    }
}


fun getCardsTest() {
    CoroutineScope(Dispatchers.IO).launch {
        val repository = Repository()
        val response = repository.getAllCards()
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                Log.v("MainAct OK", response.body()?.data[0].toString())
            } else {
                Log.e("MainAct Error:", response.toString())
            }
        }
    }
}

