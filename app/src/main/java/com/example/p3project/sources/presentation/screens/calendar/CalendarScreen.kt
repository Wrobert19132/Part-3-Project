package com.example.p3project.sources.presentation.screens.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun CalendarScreen(navController: NavController) {
    Column {
        Text("There's Nothing Here!", style=MaterialTheme.typography.titleLarge)

    }
}