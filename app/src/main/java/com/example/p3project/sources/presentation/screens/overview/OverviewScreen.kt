package com.example.p3project.sources.presentation.screens.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.p3project.sources.presentation.screens.Screen
import com.example.p3project.sources.presentation.screens.overview.components.TaskCard
import com.example.p3project.sources.presentation.shared_components.AppNavigation
import com.example.p3project.sources.presentation.shared_components.AppSnackbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen (
    navController: NavController,
    viewModel: OverviewViewmodel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember {SnackbarHostState() }
    
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(true) {
        viewModel.onEvent(OverviewEvent.ReloadTasks)
    }
    Scaffold (
        snackbarHost = {
            AppSnackbar(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text("Upcoming Tasks")
                },
            )
        },
        bottomBar = {
            AppNavigation(navController = navController, current_selected = Screen.OverviewScreen)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navController.navigate(Screen.AddtaskScreen.route)}
            ) {
                Icon(Icons.Filled.Add, "Add task")
            }
        }

    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        ) {
            LazyColumn() {
                items(state.tasks) { task ->
                    TaskCard(
                        task = task,
                        onClick = {
                            navController.navigate(
                                Screen.ViewTaskScreen.route + "/${task.taskId}"
                            )
                        },
                        onComplete = {}
                    )
                }
            }

        }
    }
}

