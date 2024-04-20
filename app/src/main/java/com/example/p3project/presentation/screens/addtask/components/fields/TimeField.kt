package com.example.p3project.presentation.screens.addtask.components.fields

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.p3project.presentation.screens.sharedComponents.ClickableTextField

@Composable
fun TimeField(value: String, onClick: (() -> Unit)) {
    ClickableTextField(
        value = "at: $value",
        Modifier.width(110.dp),
        onClick = onClick
    )
}