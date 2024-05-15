package com.justvalue.justofferts.ui.main

import android.content.Context
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
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
import java.io.File
import java.io.FileOutputStream

@Composable
fun CreateOfferScreen(navController: NavHostController) {
    val context = LocalContext.current

    Column {
        PdfCreator(context, navController)
    }

}

@Composable
fun PdfCreator(context : Context, navController: NavHostController) {
    var kund by remember { mutableStateOf(TextFieldValue()) }
    var kontaktperson by remember { mutableStateOf(TextFieldValue()) }


    Column {
        TextField(value = kund, onValueChange = { kund = it }, label = { Text("Kundnamn") })
        TextField(value = kontaktperson, onValueChange = { kontaktperson = it }, label = { Text("Kontaktperson") })
        Button(onClick = { generatePDF(context, navController, kund.text, kontaktperson.text) }) {
            Text("Skapa Offert")
        }
    }
}

fun generatePDF(context: Context, navController: NavHostController, kund: String, kontaktperson: String) {

    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()
    val page = pdfDocument.startPage(pageInfo)
    val canvas = page.canvas

    // Draw text on the PDF
    val text = "Kund: $kund\nKontaktperson: $kontaktperson"
    canvas.drawText(text, 40f, 50f, Paint())

    pdfDocument.finishPage(page)

    // Save the PDF to external storage
    val file = File(context.getExternalFilesDir(null), "output.pdf")
    val outputStream = FileOutputStream(file)
    pdfDocument.writeTo(outputStream)
    pdfDocument.close()
    outputStream.close()

    navController.navigate("send_offer")

}