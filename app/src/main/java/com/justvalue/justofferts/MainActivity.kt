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
                    Greeting("Android")
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
fun PdfCreator() {
    var kund by remember { mutableStateOf(TextFieldValue()) }
    var kontaktperson by remember { mutableStateOf(TextFieldValue()) }

    Column {
        TextField(value = kund, onValueChange = { kund = it }, label = { Text("Kundnamn") })
        TextField(value = kontaktperson, onValueChange = { kontaktperson = it }, label = { Text("Kontaktperson") })
        Button(onClick = { generatePDF(kund.text, kontaktperson.text) }) {
            Text("Generate PDF")
        }
    }
}


fun generatePDF(context: Context, kund: String, kontaktperson: String) {

    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()
    val page = pdfDocument.startPage(pageInfo)
    val canvas = page.canvas

    // Draw text on the PDF
    val text = "Kund: $kund\nKontaktperson: $kontaktperson"
    canvas.drawText(text, 40f, 50f, Paint())

    // Save the PDF to external storage
    val file = File(context.getExternalFilesDir(null), "output.pdf")
    val outputStream = FileOutputStream(file)
    pdfDocument.writeTo(outputStream)
    pdfDocument.close()
    outputStream.close()

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JustOffertsTheme {
        Greeting("Android")
    }
}