package com.example.p3project.presentation.screens.settings.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.p3project.domain.model.Category

@Composable
fun CategoryDialog(category: Category?, onDelete: (() -> Unit), onDismiss: (() -> Unit)) {
    if (category != null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    onClick = onDelete
                ) { Text(text = "Delete", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismiss
                ) {
                    Text(text = "Close")
                }
            },
            title = {
                Text(text = category.categoryName,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = { Text(text = "A category that can be used to classify your Tasks.")},
            tonalElevation = 50.dp,
        )
    }
}