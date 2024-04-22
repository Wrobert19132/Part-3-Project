package com.example.p3project.presentation.screens.taskview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.presentation.screens.Screen
import com.example.p3project.presentation.screens.sharedComponents.AppConfirmDialog
import com.example.p3project.presentation.screens.sharedComponents.TaskCompletionButton
import com.example.p3project.presentation.screens.sharedComponents.TaskTime
import com.example.p3project.presentation.screens.taskview.components.StreakCircle
import com.example.p3project.presentation.screens.taskview.components.StreakCompletionPie
import com.example.p3project.presentation.screens.taskview.components.StreakInfo
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

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
        val taskInfo: TaskWithRelations = state.taskInfo!!
        val task: Task = taskInfo.task

        AppConfirmDialog(state.askDelete,
            "Are you sure you want to delete this task?",
            {
                viewModel.onEvent(TaskScreenEvent.confirmDelete)
                navController.popBackStack()
            },
            {viewModel.onEvent(TaskScreenEvent.toggleDeleteWarning(false))},
            "Confirm Deletion",
            "Delete")

        val scroll = TopAppBarDefaults.enterAlwaysScrollBehavior(
            rememberTopAppBarState()
        )

        Scaffold(
            modifier = Modifier.nestedScroll(scroll.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(),
                    scrollBehavior = scroll,
                    title = {},
                    actions = {
                        IconButton(
                            onClick = {
                                viewModel.onEvent(TaskScreenEvent.toggleDeleteWarning(true))
                            }
                        ) {
                            Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
                        }

                        IconButton(
                            onClick = {
                                viewModel.onEvent(TaskScreenEvent.toggleDeleteWarning(true))
                            }
                        ) {
                            if (task.enabled) {
                                Icon(
                                    imageVector = Icons.Filled.Check, contentDescription = "Task Enabled"
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Filled.Close, contentDescription = "Task Disabled"
                                )
                            }

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
            LazyColumn(
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        task.name,
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineLarge
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TaskTime(
                        task = task,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Box(
                        Modifier
                            .fillMaxWidth()
                            .wrapContentSize()
                    ) {
                        StreakCircle(taskInfo = state.taskInfo!!, from = LocalDate.now())
                    }
                }

                item {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .wrapContentSize()
                    ) {
                        TaskCompletionButton(state.taskInfo!!,
                            onComplete = { viewModel.onEvent(TaskScreenEvent.CompleteTask(true)) },
                            onUncomplete = { viewModel.onEvent(TaskScreenEvent.CompleteTask(false)) }
                        )
                    }
                }

                item {
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 100.dp)
                            .padding(2.dp)
                    ) {
                        Text(
                            task.description,
                            Modifier.padding(16.dp)
                        )
                    }
                }

                item {
                    ElevatedCard {
                        Column (verticalArrangement = Arrangement.spacedBy(5.dp))
                        {

                            ListItem(leadingContent = { Text("Target Time:") },
                                headlineContent = {
                                    Text(
                                        String.format(
                                            "%02d:%02d",
                                            task.targetTime.hour, task.targetTime.minute
                                        )
                                    )
                                }
                            )
                            ListItem(leadingContent = { Text("Started:") },
                                headlineContent = {
                                    Text(
                                        task.startDate.format(
                                            DateTimeFormatter.ofLocalizedDate(
                                                FormatStyle.FULL
                                            )
                                        )
                                    )
                                }
                            )

                            ListItem(leadingContent = { Text("Notifications are sent:") },
                                headlineContent = {
                                    Text("${taskInfo.task.notificationOffset} minutes before")
                                }
                            )
                        }
                    }
                }

                item {
                    ElevatedCard {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            text = "All-time Info",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                            ListItem(leadingContent = { Text("Total Completions:") },
                                headlineContent = {
                                    Text("${taskInfo.totalCompletions()} Completions")
                                }
                            )
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                            ListItem(leadingContent = { Text("Days since start:") },
                                headlineContent = {
                                    Text("${(LocalDate.now().toEpochDay() - task.startDate.toEpochDay())} days")
                                }
                            )
                        }
                    }
                }

                item {
                    StreakInfo(title = "Your Current Streak",
                        streak = taskInfo.streakFrom(LocalDate.now()),
                        task = task
                    )
                }

                item {
                    StreakCompletionPie(state.currentCompletionData!!,
                                        state.currentStreak!!.size() != 0
                    )
                }

                item {
                    StreakInfo(title = "Your Best Streak",
                               streak = taskInfo.longestStreak(),
                               task = task
                    )
                }



                item {
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    }

}

