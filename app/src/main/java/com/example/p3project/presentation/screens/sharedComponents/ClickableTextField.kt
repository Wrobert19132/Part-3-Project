package com.example.p3project.presentation.screens.sharedComponents

import androidx.compose.foundation.clickable
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun ClickableTextField(value: String, modifier: Modifier = Modifier, showEnabled: Boolean = true, onClick: (() -> Unit)) {
    var defaultCols = OutlinedTextFieldDefaults.colors()
    if (showEnabled) {
        defaultCols = defaultCols.copy(
            disabledContainerColor = defaultCols.unfocusedContainerColor,
            disabledTextColor = defaultCols.unfocusedTextColor,
            disabledIndicatorColor = defaultCols.unfocusedIndicatorColor,
            disabledPlaceholderColor = defaultCols.unfocusedPlaceholderColor
        )
    }
    OutlinedTextField(
        enabled = false,
        onValueChange = {},
        modifier = modifier.clickable { onClick() },
        value = value,
        colors = defaultCols
    )
}