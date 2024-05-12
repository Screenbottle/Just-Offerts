package com.justvalue.justofferts.ui.main

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    Button(onClick = {
        navController.navigate("create_offer")
    }) {
        Text("Go to Create Offer")
    }
}
