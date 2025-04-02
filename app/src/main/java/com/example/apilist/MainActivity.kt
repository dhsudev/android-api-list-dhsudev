package com.example.apilist

import android.os.Bundle
import android.provider.ContactsContract.Contacts.Data
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.apilist.data.network.ApiInterface
import com.example.apilist.data.network.Repository
import com.example.apilist.ui.theme.APIListTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getCardsTest()
        enableEdgeToEdge()
        setContent {
            APIListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
fun getCardsTest(){
    CoroutineScope(Dispatchers.IO).launch {

        val repository = Repository()
        val response = repository.getAllCards()
        withContext(Dispatchers.Main) {
            if(response.isSuccessful){
                Log.v("MainAct OK", response.body()?.data[0].toString())
            }
            else{
                Log.e("MainAct Error:", response.toString())
            }
        }
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    APIListTheme {
        Greeting("Android")
    }
}*/
