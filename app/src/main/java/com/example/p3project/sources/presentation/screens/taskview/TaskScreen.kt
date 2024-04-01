package com.example.p3project.sources.presentation.screens.taskview

import com.example.p3project.sources.presentation.screens.overview.OverviewViewmodel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen (
    navController: NavController,
    taskID: Int,
    viewModel: OverviewViewmodel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember {SnackbarHostState() }

    val state = viewModel.state.collectAsState().value


}

