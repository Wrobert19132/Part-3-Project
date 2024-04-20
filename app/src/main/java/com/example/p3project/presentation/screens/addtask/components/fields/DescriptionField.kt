package com.example.p3project.presentation.screens.addtask.components.fields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.p3project.domain.model.Task
import com.example.p3project.presentation.screens.addtask.AddTaskEvent

@Composable
fun DescriptionField(value: String, onValueChange: ((String) -> Unit)) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        label = {
            Text(text = "Task Description")
        },
        value = value,
        isError = (value.length >= Task.maxDescriptionLength),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        onValueChange = onValueChange,
    )
}