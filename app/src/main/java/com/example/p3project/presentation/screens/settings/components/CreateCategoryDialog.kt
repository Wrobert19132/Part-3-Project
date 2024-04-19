package com.example.p3project.presentation.screens.settings.components

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

@Composable
fun CreateCategoryDialog(visible: Boolean,
                         onConfirm: ((categoryName: String, categoryColor: Color) -> Unit),
                         onDismiss: (() -> Unit)) {

    var text by remember{mutableStateOf("")}

    LaunchedEffect(visible) {
        text = ""
    }

    if (visible) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    onClick = {onConfirm(text, Color.Blue)}
                ) { Text(text = "Add") }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismiss
                ) {
                    Text(text = "Cancel")
                }
            },
            title = {
                Text(text = "Create a Category",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                TextField(text,
                    isError = (text.length == Category.maxNameLength),
                    onValueChange = {new -> if (new.length <= Category.maxNameLength) text = new})
            },
            tonalElevation = 50.dp,
        )
    }
}