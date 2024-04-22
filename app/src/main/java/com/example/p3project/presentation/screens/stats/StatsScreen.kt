package com.example.p3project.presentation.screens.stats

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun StatsScreen(navController: NavController,
                viewModel: StatsViewModel = hiltViewModel()
) {
    Column {
        Text("There's Nothing Here!", style=MaterialTheme.typography.titleLarge)

    }
}