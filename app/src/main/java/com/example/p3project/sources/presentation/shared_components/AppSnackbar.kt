package com.example.p3project.sources.presentation.shared_components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable

@Composable
fun AppSnackbar(hostState: SnackbarHostState) {
    SnackbarHost(hostState = hostState) { data ->
        Snackbar(
            snackbarData = data,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary
        )
    }
}