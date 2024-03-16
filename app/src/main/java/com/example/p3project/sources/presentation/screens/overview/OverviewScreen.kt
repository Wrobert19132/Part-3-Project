package com.example.p3project.sources.presentation.screens.overview

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Label
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.p3project.sources.data.Task
import com.example.p3project.sources.presentation.screens.Screen
import com.example.p3project.sources.presentation.screens.overview.components.TaskCard
import dagger.hilt.android.lifecycle.HiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen (
    navController: NavController,
    viewModel: OverviewViewmodel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    Scaffold (
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
            NavigationBar(){
                NavigationBarItem(
                    icon = {Icon(Icons.Filled.Home, "")},
                    selected = true,
                    onClick = { },
                )
                NavigationBarItem(
                    icon = {Icon(Icons.Filled.Person, "")},
                    selected = false,
                    onClick = { },
                )
                NavigationBarItem(
                    icon = {Icon(Icons.Filled.ThumbUp, "")},
                    selected = false,
                    onClick = { },
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {viewModel.onEvent(
                    OverviewEvent.AddTask(task = Task("Test task",
                        (Math.random()*1000).toInt(), "Nananannananananannanananana, REPENT")))}) {

            }}

    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
            content = {
                LazyColumn() {
                    items(state.tasks) { task ->
                        TaskCard(
                            task = task
                        ) {}
                    }
                }
            }
        )
    }
}

