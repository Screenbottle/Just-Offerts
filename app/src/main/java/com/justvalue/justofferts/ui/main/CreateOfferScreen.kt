package com.justvalue.justofferts.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.provider.MediaStore
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
import java.time.LocalDate
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.justvalue.justofferts.R
import com.justvalue.justofferts.ui.theme.Optima
import kotlin.math.exp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOfferScreen(navController: NavHostController) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarColors(
                    containerColor = colorResource(id = R.color.primary_container),
                    titleContentColor = colorResource(id = R.color.black),
                    navigationIconContentColor = colorResource(id = R.color.black),
                    actionIconContentColor = colorResource(id = R.color.black),
                    scrolledContainerColor = colorResource(id = R.color.black)
                ),
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold, fontFamily = Optima)
                    )
                },
                actions = {

                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PdfCreator(context, navController, uriHandler)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdfCreator(context : Context, navController: NavHostController, uriHandler: UriHandler) {
    var kund by remember { mutableStateOf(TextFieldValue()) }
    var kontaktperson by remember { mutableStateOf(TextFieldValue()) }
    var expanded by remember { mutableStateOf(false) }


    val options = listOf("Pölen", "Plaskdammen", "Bäcken", "Ån", "Forsen", "Floden")
    var selectedOption by remember { mutableStateOf(options.first()) }



    Column(
        modifier = Modifier
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Fyll i information",
            style = TextStyle(fontSize = 20.sp, fontFamily = Optima, fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(value = kund, onValueChange = { kund = it }, label = { Text("Kundnamn") })
        TextField(value = kontaktperson, onValueChange = { kontaktperson = it }, label = { Text("Kontaktperson") })
        Spacer(modifier = Modifier.height(10.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false
                }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option) },
                        onClick = {
                            selectedOption = option
                            expanded = false

                        })
                }
            }

        }

        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = { generatePDF(context, navController, kund.text, kontaktperson.text, selectedOption) },
            modifier = Modifier
                .size(width = 200.dp, height = 72.dp),
            enabled = kontaktperson.text.isNotBlank() && kund.text.isNotBlank()
        ) {
            Text(
                "Skapa Offert",
                style = TextStyle(fontSize = 20.sp, fontFamily = Optima)
            )
        }


    }
}




fun generatePDF(context: Context, navController: NavHostController, kund: String, kontaktperson: String, selectedOption: String) {

    val currentDate = LocalDate.now()
    val dateString = currentDate.toString()

    val pdfName = "$kund $dateString"

    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()
    val page = pdfDocument.startPage(pageInfo)
    val canvas = page.canvas

    // Draw text on the PDF
    val text = "Kund: $kund \nKontaktperson: $kontaktperson \nPaket: $selectedOption"
    canvas.drawText(text, 40f, 50f, Paint())

    pdfDocument.finishPage(page)

    // Save the PDF to external storage
    val documentsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
    val pdfFile = File(documentsFolder, "$pdfName.pdf")
    val outputStream = FileOutputStream(pdfFile)

    // Write PDF content to outputStream
    pdfDocument.writeTo(outputStream)
    pdfDocument.close()

    // Notify system that a new file has been added
    context.applicationContext.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(pdfFile)))

    navController.navigate("send_offer")

}
