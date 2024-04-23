package com.example.p3project.presentation.screens.stats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.p3project.presentation.screens.Screen
import com.example.p3project.presentation.screens.sharedComponents.AppNavigation
import com.example.p3project.presentation.screens.sharedComponents.AppSnackbar
import com.example.p3project.presentation.screens.stats.components.CategoriesPie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(navController: NavController,
                viewModel: StatsViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val state = viewModel.state.collectAsState().value

    LaunchedEffect(true) {
        viewModel.onEvent(StatsScreenEvent.Reload)
    }

    Scaffold(
        snackbarHost = {
            AppSnackbar(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = {
                    Text("Task Stats")
                },
            )
        },
        bottomBar = {
            AppNavigation(navController = navController, current_selected = Screen.StatScreen)
        },
        floatingActionButton = {
        }

    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues)
                .padding(10.dp)
        ) {
            if (state.dataLoaded) {
                CategoriesPie(shown = (state.categoryUsage!!.totalLength > 0),
                    completionData = state.categoryUsage!!)

            }
        }
    }
}