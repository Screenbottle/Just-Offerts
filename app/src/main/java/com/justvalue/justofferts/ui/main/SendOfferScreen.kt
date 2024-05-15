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
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.File

@Composable
fun SendOfferScreen(navController: NavHostController) {
    val context = LocalContext.current
    var emailAddress by remember { mutableStateOf(TextFieldValue()) }
    var selectedPdfUri by remember { mutableStateOf<Uri?>(null) }
    val appFilesDirectory = File(LocalContext.current.getExternalFilesDir(null), "")
    val initialUri = if (appFilesDirectory.exists()) Uri.fromFile(appFilesDirectory) else null

    val launcher = selectPdf { uri ->
        selectedPdfUri = uri
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Fyll i information")

        TextField(value = emailAddress, onValueChange = { emailAddress = it }, label = { Text("E-post") })

        Button(onClick = {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "application/pdf"
                // Set initial directory
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, initialUri)
            }
            launcher.launch(intent)
        }) {
            Text("Select PDF")
        }

        selectedPdfUri?.let {
            Text("Selected PDF: ${it.path?.let { it1 -> File(it1).name }}")
        }

        Button(
            onClick = { sendEmailAction(emailAddress.text, context, selectedPdfUri) },
            enabled = emailAddress.text.isNotBlank() && selectedPdfUri != null
        ) {
            Text("Send Email")
        }



    }

}
fun sendEmailAction(emailAddress: String, context: Context, pdfUri: Uri?): () -> Unit {
    return {

        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "application/pdf"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
        pdfUri?.let { uri ->
            emailIntent.putExtra(Intent.EXTRA_STREAM, uri)
        }
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }
}


@Composable
fun selectPdf(onPdfSelected: (Uri?) -> Unit): ActivityResultLauncher<Intent> {
    val context = LocalContext.current as ComponentActivity
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        // Handle the result
        if (activityResult.resultCode == Activity.RESULT_OK) {
            val data: Intent? = activityResult.data
            val uri = data?.data
            onPdfSelected(uri)
        }
    }
    return launcher
}




@Composable
fun rememberLauncherForActivityResult(
    contract: ActivityResultContract<Intent, ActivityResult>,
    onResult: (Uri?) -> Unit
): ActivityResultLauncher<Intent> {
    val context = LocalContext.current as ComponentActivity
    return remember(context) {
        context.activityResultRegistry.register("key", contract) { result ->
            val data: Intent? = result.data
            val uri = data?.data
            onResult(uri)
        }
    }
}

