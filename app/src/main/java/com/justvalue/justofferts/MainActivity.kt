package com.justvalue.justofferts

import android.graphics.Paint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.justvalue.justofferts.ui.theme.JustOffertsTheme
import android.graphics.pdf.PdfDocument
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import java.io.File
import java.io.FileOutputStream
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.justvalue.justofferts.ui.main.CreateOfferScreen
import com.justvalue.justofferts.ui.main.HomeScreen
import com.justvalue.justofferts.ui.main.PreviouslyCreatedOffersScreen
import com.justvalue.justofferts.ui.main.SendOfferScreen
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.StringRes
import androidx.compose.material3.Scaffold
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.Menu


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JustOffertsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    JustOfferts()
                }
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



@Composable
fun JustOfferts() {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = navController.currentDestination?.route == "homescreen",
                    onClick = { navController.navigate("homescreen") },
                    icon = { Icons.Default.Home },
                    label = { Text(text = "Start") }
                )
                NavigationBarItem(
                    selected = navController.currentDestination?.route == "create_offer",
                    onClick = { navController.navigate("create_offer") },
                    icon = { Icons.Default.Add },
                    label = { Text(text = "Skapa Offert") }
                )
                NavigationBarItem(
                    selected = navController.currentDestination?.route == "send_offer",
                    onClick = { navController.navigate("send_offer") },
                    icon = { Icons.Default.Email },
                    label = { Text(text = "Skicka Offert") }
                )

            }
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = "homescreen") {
            composable("homescreen") {
                HomeScreen(navController = navController, innerPadding)
            }
            composable("create_offer") {
                CreateOfferScreen(navController = navController)
            }
            composable("send_offer") {
                SendOfferScreen(navController = navController)
            }
            composable("prev_created_offers") {
                PreviouslyCreatedOffersScreen(navController = navController)
            }
        }
    }


}








@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JustOffertsTheme {
        Greeting("Android")
    }
}