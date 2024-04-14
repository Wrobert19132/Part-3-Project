package com.example.p3project.presentation.screens.taskview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.p3project.domain.model.Task
import com.example.p3project.presentation.screens.shared_components.TaskTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen (
    navController: NavController,
    taskID: Int?,
    viewModel: TaskScreenViewmodel = hiltViewModel(),

    ) {
    LaunchedEffect(true) {
        viewModel.onEvent(TaskScreenEvent.ReloadTask)
    }

    val scope = rememberCoroutineScope()

    if (viewModel.taskInfo != null) {
        val task: Task = viewModel.taskInfo!!.task


        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(),
                    title = {},
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { /* do something */ }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit Task"
                    )
                }
            }
        ) { paddingValues ->
            Column(
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
            ) {
                Text(task.name,
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge)

                TaskTime(task = task,
                         modifier = Modifier.fillMaxWidth(),
                         textAlign = TextAlign.Center,
                         style = MaterialTheme.typography.headlineSmall
                )


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

