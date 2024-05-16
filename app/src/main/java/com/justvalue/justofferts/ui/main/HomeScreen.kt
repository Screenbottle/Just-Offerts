package com.justvalue.justofferts.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.justvalue.justofferts.R
import com.justvalue.justofferts.ui.theme.Optima

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, innerPadding: PaddingValues) {
    val context = LocalContext.current

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
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(color = colorResource(id = R.color.icon_background))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.just_value_logo),
                    contentDescription = stringResource(id = R.string.logo_description),
                    modifier = Modifier
                        .height(250.dp)
                        .width(250.dp)
                        .align(Alignment.Center)
                )
            }


            Button(
                modifier = Modifier
                    .size(width = 250.dp, height = 80.dp),
                onClick = {
                    navController.navigate("create_offer")
                }
            ) {
                Text(
                    "Skapa Offert",
                    style = TextStyle(fontSize = 25.sp, fontFamily = Optima)
                )
            }
        }
    }



}
