package com.example.p3project.presentation.screens.sharedComponents

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun AppError(errorMessage: String?, onDismiss: (() -> Unit)) {
    if (errorMessage != null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    onClick = onDismiss
                ) { Text(text = "Okay") }
            },
            text = { Text(text = errorMessage) },
            tonalElevation = 50.dp,
        )
    }
}