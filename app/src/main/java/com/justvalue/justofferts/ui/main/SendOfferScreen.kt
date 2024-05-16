package com.justvalue.justofferts.ui.main

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavHostController
import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.File
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.justvalue.justofferts.R
import com.justvalue.justofferts.ui.theme.Optima


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendOfferScreen(navController: NavHostController) {
    val context = LocalContext.current
    var emailAddress by remember { mutableStateOf(TextFieldValue()) }
    var selectedPdfUri by remember { mutableStateOf<Uri?>(null) }


    val lifecycleOwner = LocalLifecycleOwner.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        // Handle the selected file URI
        uri?.let { documentUri ->
            lifecycleOwner.lifecycleScope.launch {
                val documentFile = DocumentFile.fromSingleUri(context, documentUri)
                documentFile?.let { file ->
                    selectedPdfUri = file.uri
                }
            }
        }
    }

    // Function to send email with PDF attachment
    fun sendEmailWithAttachment() {
        selectedPdfUri?.let { pdfUri ->
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "application/pdf"
            intent.putExtra(Intent.EXTRA_STREAM, pdfUri)
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress.text))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
            intent.putExtra(Intent.EXTRA_TEXT, "Body")
            context.startActivity(Intent.createChooser(intent, "Send Email"))
        }
    }


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

            Text(
                text = "Fyll i information",
                style = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold, fontFamily = Optima)
                )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = emailAddress,
                onValueChange = { emailAddress = it },
                label = { Text("E-post") })

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                launcher.launch(arrayOf("application/pdf"))
            }) {
                Text("Välj Offert")
            }

            selectedPdfUri?.let {
                Text("Vald Offert: ${it.path?.let { it1 -> File(it1).name }}")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { sendEmailWithAttachment() },
                enabled = emailAddress.text.isNotBlank() && selectedPdfUri != null
            ) {
                Text("Skicka Offert")
            }


        }
    }

}

