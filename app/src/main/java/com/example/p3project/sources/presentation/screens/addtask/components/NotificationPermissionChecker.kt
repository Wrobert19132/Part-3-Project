package com.example.p3project.sources.presentation.screens.addtask.components

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

private fun shouldAskForNotificationPermission(activity: Activity): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        return (ActivityCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
    } else {
        TODO("VERSION.SDK_INT < TIRAMISU")
    }
}

fun shouldAskForSchedulerPermission(activity: Activity): Boolean {
    val alarmManager = ContextCompat.getSystemService(activity, AlarmManager::class.java)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        return !alarmManager!!.canScheduleExactAlarms()
    } else {
        TODO("VERSION.SDK_INT < S")
    }
}

@Composable
fun PermissionChecker(onNotificationFail: (() -> Unit), onSchedulerFail: (() -> Unit)) {
    val activity = LocalContext.current as Activity



    var showNotificationPrompt by remember { mutableStateOf(false)}
    var showAlarmPrompt by remember { mutableStateOf(false)}

    LaunchedEffect(true) {
        showNotificationPrompt = shouldAskForNotificationPermission(activity)
        showAlarmPrompt = shouldAskForSchedulerPermission(activity)
    }
    if (showNotificationPrompt) {
        AlertDialog(
            icon = { Icon(Icons.Default.Notifications, "A notification bell Icon") },
            text = {
                Text(
                    text = "This application relies on digital interrupts to function. " +
                            "To send these, it needs permission to send notifications."
                )
            },
            onDismissRequest = {
                showNotificationPrompt = false
                onNotificationFail()
            },
            confirmButton = {
                TextButton(onClick = {
                    ActivityCompat.requestPermissions(activity,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        0)
                    showNotificationPrompt = false
                }
                ) {
                    Text(text = "Okay")

                }


            },
            dismissButton = {
                TextButton(onClick = {
                    showNotificationPrompt = false
                    onNotificationFail()
                }
                ) {
                    Text(text = "Dismiss")

                }
            }
        )
    } else if (showAlarmPrompt) {
        AlertDialog(
            icon = { Icon(Icons.Default.DateRange, "A calendar Icon") },
            text = {
                Text(
                    text = "This application relies on digital interrupts to function. " +
                            "To send these, it needs permission to schedule notifications."
                )
            },
            onDismissRequest = {
                showAlarmPrompt = false
                onNotificationFail()
            },
            confirmButton = {
                TextButton(onClick = {
                    Intent().also { intent ->
                        intent.action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                        activity.startActivity(intent)
                    }
                    showAlarmPrompt = false
                }
                ) {
                    Text(text = "Okay")

                }


            },
            dismissButton = {
                TextButton(onClick = {
                    showAlarmPrompt = false
                    onSchedulerFail()
                }
                ) {
                    Text(text = "Dismiss")

                }
            }
        )
    }
}