package com.example.p3project.presentation.overview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.p3project.presentation.Screen

@Composable
fun OverviewScreen (
    navController: NavController,
    viewModel: OverviewViewModel = viewModel()
) {
    var state = viewModel.state.value
    Column {
        var text by remember {mutableStateOf("")}


        Text(text = "P3 Project Test Screen", style = MaterialTheme.typography.headlineLarge )
        Text(text = "This is a test / demo screen for my P3 Project.",
            style = MaterialTheme.typography.bodyMedium )

        Text(text = "Notifications Sent: " + state.notifications_sent,
             style = MaterialTheme.typography.bodySmall
        )
        Row() {
            Text(text = "Move to other page:") 
            Button(onClick = {navController.navigate(Screen.CalendarScreen.route)}) {

            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.align(Alignment.Center))
            {
                Row (
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) { Text(text = "Send Notification") }

                Row (
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    TextField(text, onValueChange = { text = it})
                }

                Row (
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ){ Button(
                    onClick = {
                        viewModel.sendNotification(text)
                        text = ""
                    }) {}
                }

            }
        }
    }
}