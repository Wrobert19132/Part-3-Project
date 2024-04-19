package com.example.p3project.presentation.screens.sharedComponents;

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.p3project.domain.model.Category
import kotlin.concurrent.timerTask

@Composable
fun AppConfirmDialog(visible: Boolean,
                  text: String,
                  onConfirm: (() -> Unit),
                  onDismiss: (() -> Unit),
                  title: String = "",
                  confirmText: String = "Confirm"
) {
    if (visible) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    onClick = onConfirm
                ) { Text(text = confirmText) }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismiss
                ) {
                    Text(text = "Cancel")
                }
            },
            title = {
                Text(text = title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {Text(text = text)},
            tonalElevation = 50.dp,
        )
    }
}
