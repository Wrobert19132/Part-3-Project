package com.example.p3project.presentation.screens.addtask.components.fields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import com.example.p3project.domain.model.Task
import com.example.p3project.presentation.screens.addtask.AddTaskEvent

@Composable
fun NameField(value: String, onValueChange: ((String) -> Unit)) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(text = "Task Name")
        },
        leadingIcon = { Icon(Icons.Default.Create, "Set Task Name") },
        value = value,
        isError = (value.length >= Task.maxNameLength),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        onValueChange = onValueChange,
        singleLine = true,
    )
}