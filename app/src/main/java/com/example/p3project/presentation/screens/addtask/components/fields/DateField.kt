package com.example.p3project.presentation.screens.addtask.components.fields

import androidx.compose.runtime.Composable
import com.example.p3project.presentation.screens.sharedComponents.ClickableTextField

@Composable
fun DateField(value: String, onClick: (() -> Unit), enabled: Boolean, failAction: (() -> Unit)) {
    ClickableTextField(
        value = "Starting on $value",
        onClick = {if (enabled) {onClick()} else {failAction()}},
        showEnabled = enabled
    )
}