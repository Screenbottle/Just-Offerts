package com.justvalue.justofferts.ui.main

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.justvalue.justofferts.generatePDF

@Composable
fun CreateOfferScreen(navController: NavHostController) {
    val context = LocalContext.current

    Column {
        Button(onClick = {
            navController.navigate("send_offer")
        }) {
            Text("Skapa Offert")
        }
        PdfCreator(context)
    }

}

@Composable
fun PdfCreator(context : Context) {
    var kund by remember { mutableStateOf(TextFieldValue()) }
    var kontaktperson by remember { mutableStateOf(TextFieldValue()) }


    Column {
        TextField(value = kund, onValueChange = { kund = it }, label = { Text("Kundnamn") })
        TextField(value = kontaktperson, onValueChange = { kontaktperson = it }, label = { Text("Kontaktperson") })
        Button(onClick = { generatePDF(context, kund.text, kontaktperson.text) }) {
            Text("Generate PDF")
        }
    }
}