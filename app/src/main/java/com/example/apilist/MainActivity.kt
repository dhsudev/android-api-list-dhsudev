package com.example.apilist

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.apilist.data.network.Repository
import com.example.apilist.ui.navigation.Destinations
import com.example.apilist.ui.navigation.NavWrapper
import com.example.apilist.ui.navigation.NavigationItem
import com.example.apilist.viewmodel.ListApiViewModel
import com.example.compose.APIListTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.Int

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
                MyApp()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyApp() {
    val vm: ListApiViewModel = viewModel()
    val userSettings by vm.userSettings  // Obtén la configuración de usuario

    if (userSettings == null) {
        // Mostrar un indicador de carga mientras se obtiene la configuración
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    // Aplicar el tema dinámicamente basado en isDarkTheme
    APIListTheme(darkTheme = userSettings!!.isDarkTheme) {
        MainScaffold(vm = vm)  // Aquí usas la UI principal con el tema aplicado
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(vm: ListApiViewModel) {
    var selectedItem: Int by remember { mutableIntStateOf(0) }

    val items = listOf(
        NavigationItem("List", Icons.Default.Menu, Destinations.ListScreen, 0),
        NavigationItem("Favourites", Icons.Default.Favorite, Destinations.FavScreen, 1),
        NavigationItem("Settings", Icons.Default.Settings, Destinations.SettingsScreen, 2)
    )
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.letras),
                        contentDescription = "Logo"
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.primaryContainer) {
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
                        },
                        colors = NavigationBarItemDefaults.colors(
                            unselectedIconColor = MaterialTheme.colorScheme.secondary,
                            unselectedTextColor = MaterialTheme.colorScheme.secondary,
                            indicatorColor = MaterialTheme.colorScheme.tertiaryContainer,
                            selectedTextColor = MaterialTheme.colorScheme.tertiary,
                            selectedIconColor = MaterialTheme.colorScheme.tertiary
                        )
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        NavWrapper(navController, Modifier.padding(innerPadding), vm)
    }
}




