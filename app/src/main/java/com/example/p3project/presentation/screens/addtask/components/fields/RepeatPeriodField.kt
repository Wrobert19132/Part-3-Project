package com.example.p3project.presentation.screens.addtask.components.fields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.p3project.presentation.screens.addtask.AddTaskEvent


@Composable
fun RepeatPeriodField(value: String, onValueChange: ((String) -> Unit), enabled: Boolean, failAction: (() -> Unit)) {
    OutlinedTextField(
        modifier = Modifier
            .width(210.dp)
            .clickable { if (!enabled) {failAction()} },
        prefix = {
            Text(text = "Repeat every ")
        },
        suffix = {
            Text(text = " days")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        value = value,
        enabled = enabled,
        onValueChange = onValueChange
    )
}