package com.example.p3project.presentation.screens.addtask.components.fields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.p3project.presentation.screens.addtask.AddTaskEvent

@Composable
fun NotificationField(value: String, onValueChange: ((String) -> Unit)) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        prefix = {
            Text(text = "Send notification ")
        },
        suffix = {
            Text(text = " minutes before target")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        value = value,
        onValueChange = onValueChange
    )
}