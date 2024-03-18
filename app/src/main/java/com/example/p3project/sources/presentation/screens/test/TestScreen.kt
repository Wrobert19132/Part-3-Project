package com.example.p3project.sources.presentation.screens.test

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.p3project.sources.presentation.screens.Screen
import com.example.p3project.sources.presentation.shared_components.AppNavigation

@Composable
fun TestScreen (
    navController: NavController,
    viewModel: TestViewModel = viewModel()
) {
    val notificationPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            viewModel.onPermissionResult(
                permission = Manifest.permission.POST_NOTIFICATIONS,
                isGranted = isGranted
            )
        }
    )



    var state = viewModel.state.value
    Scaffold (
        bottomBar = {
            AppNavigation(navController = navController, current_selected = Screen.TestScreen)
        }
    ) {paddingValues ->
        Column (Modifier.padding(paddingValues)){
            var text by remember { mutableStateOf("") }


            Text(text = "P3 Project Test Screen", style = MaterialTheme.typography.headlineLarge)
            Text(
                text = "This is a test / demo screen for my P3 Project.",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Notifications Sent: " + state.notifications_sent,
                style = MaterialTheme.typography.bodySmall
            )
            Row() {
                Text(text = "Move to other page:")
                Button(onClick = { navController.navigate(Screen.CalendarScreen.route) }) {

                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.align(Alignment.Center))
                {
                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) { Text(text = "Send Notification") }

                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        TextField(text, onValueChange = { text = it })
                    }

                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Button(
                            onClick = {
                                notificationPermissionResultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                viewModel.sendNotification(text)
                                text = ""
                            }) {}
                    }

                }
            }
        }
    }
}