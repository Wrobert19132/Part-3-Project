package com.example.p3project.presentation.screens.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.example.p3project.presentation.screens.calendar.components.CalendarCard
import com.example.p3project.presentation.screens.overview.OverviewEvent
import com.example.p3project.presentation.screens.overview.OverviewViewmodel
import com.example.p3project.presentation.screens.sharedComponents.AppNavigation
import com.example.p3project.presentation.screens.sharedComponents.AppSnackbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen (
    navController: NavController,
    viewModel: CalendarViewModel = hiltViewModel()
) {

    val snackbarHostState = remember { SnackbarHostState() }

    val state = viewModel.state.collectAsState().value

    LaunchedEffect(true) {
        viewModel.onEvent(CalendarEvent.ReloadInfo)
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
                    Text("Calendar view")
                },
            )
        },
        bottomBar = {
            AppNavigation(navController = navController, current_selected = Screen.CalendarScreen)
        },
        floatingActionButton = {
        }

    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .padding(10.dp)
        ) {
            CalendarCard(state.currentDate, state.tasksInfo, { day -> },
                { viewModel.onEvent(CalendarEvent.AdjustMonth(-1)) },
                { viewModel.onEvent(CalendarEvent.AdjustMonth(1)) }
            )
        }
    }
}
