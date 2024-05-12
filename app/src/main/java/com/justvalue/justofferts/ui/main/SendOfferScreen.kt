package com.justvalue.justofferts.ui.main

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun SendOfferScreen(navController: NavHostController) {
    val context = LocalContext.current
    Button(onClick = {
        navController.navigate("send_offer")
    }) {
        Text("Skapa Offert")
    }
}