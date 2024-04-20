package com.example.p3project.presentation.screens.taskview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.p3project.domain.model.Task
import com.example.p3project.presentation.screens.Screen
import com.example.p3project.presentation.screens.sharedComponents.AppConfirmDialog
import com.example.p3project.presentation.screens.sharedComponents.TaskTime
import com.example.p3project.presentation.screens.taskview.components.StreakCircle
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen (
    navController: NavController,
    viewModel: TaskScreenViewmodel = hiltViewModel(),

    ) {
    LaunchedEffect(true) {
        viewModel.onEvent(TaskScreenEvent.ReloadTask)
    }

    val scope = rememberCoroutineScope()

    val state = viewModel.state.collectAsState().value


    if (state.taskInfo != null) {
        val task: Task = state.taskInfo!!.task

        AppConfirmDialog(state.askDelete,
            "Are you sure you want to delete this task?",
            {
                viewModel.onEvent(TaskScreenEvent.confirmDelete)
                navController.popBackStack()
            },
            {viewModel.onEvent(TaskScreenEvent.toggleDeleteWarning(false))},
            "Confirm Deletion",
            "Delete")

        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(),
                    title = {},
                    actions = {
                        IconButton(
                            onClick = {
                                viewModel.onEvent(TaskScreenEvent.toggleDeleteWarning(true))
                            }
                        ) {
                            Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {navController.navigate(Screen.AddtaskScreen.route + "?${task.taskId}")}) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit Task"
                    )
                }
            }
        ) {
            paddingValues ->
            Column(
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(task.name,
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge
                )
                
                Spacer(modifier = Modifier.height(50.dp))

                TaskTime(task = task,
                         modifier = Modifier.fillMaxWidth(),
                         textAlign = TextAlign.Center,
                         style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(30.dp))

                Box(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentSize()) {
                    StreakCircle(taskInfo = state.taskInfo!!, from = LocalDate.now())
                }

                Spacer(modifier = Modifier.height(20.dp))


                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 100.dp)
                        .padding(2.dp)
                ) {
                    Text(task.description,
                        Modifier.padding(16.dp)
                    )
                }
            }
        }
    }

}

