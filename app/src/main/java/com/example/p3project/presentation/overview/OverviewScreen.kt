package com.example.p3project.presentation.overview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun OverviewScreen (
    navController: NavController,
    viewModel: OverviewViewModel = viewModel()
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column (modifier = Modifier.fillMaxSize().align(Alignment.Center))
        {
            Row { Text(text = viewModel.state.value.text) }
            Row { Button(onClick = { viewModel.test() }) {} }
            Row { Text(text = viewModel.state.value.text2) }
        }
    }
}