package com.example.p3project.presentation.screens.addtask

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.p3project.presentation.screens.addtask.components.DatePickerDialog
import com.example.p3project.presentation.screens.addtask.components.fields.CategorySelector
import com.example.p3project.presentation.screens.addtask.components.fields.DateField
import com.example.p3project.presentation.screens.addtask.components.PermissionChecker
import com.example.p3project.presentation.screens.addtask.components.fields.RepeatPeriodField
import com.example.p3project.presentation.screens.addtask.components.fields.TimeField
import com.example.p3project.presentation.screens.addtask.components.TimePickerDialog
import com.example.p3project.presentation.screens.addtask.components.fields.DescriptionField
import com.example.p3project.presentation.screens.addtask.components.fields.NameField
import com.example.p3project.presentation.screens.addtask.components.fields.NotificationField
import com.example.p3project.presentation.screens.sharedComponents.AppError
import com.example.p3project.presentation.screens.sharedComponents.AppSnackbar
import com.example.p3project.presentation.screens.sharedComponents.KeyboardAdjust
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen (
    navController: NavController,
    viewModel: AddTaskScreenViewmodel = hiltViewModel(),
) {

    val snackbarHostState = remember { SnackbarHostState() }


    val keyboardController = LocalSoftwareKeyboardController.current
    val state = viewModel.state.collectAsState().value

    val scope = rememberCoroutineScope()

    LaunchedEffect(state.taskAdded) {
        if (state.taskAdded) {
            navController.popBackStack()
        }
    }

    LaunchedEffect(true) {
        viewModel.onEvent(AddTaskEvent.Reload)
    }



    Scaffold (
        snackbarHost = {
            AppSnackbar(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    if (state.modifying) {
                        Text("Modify a Task")
                    } else {
                        Text("Add a Task")
                    }
                },
            )
        },
        bottomBar = {
            BottomAppBar(
            ) {
                Row(Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.Right) {
                    TextButton(onClick = { navController.popBackStack() }) {
                        Text(text = "Cancel")
                    }
                    Button(onClick = {
                        keyboardController?.hide()
                        viewModel.onEvent(AddTaskEvent.AddTask)
                    }
                    ) {
                        if (state.modifying) {
                            Text(text = "Update")
                        } else {
                            Text(text = "Add")
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        TimePickerDialog(visible = state.timePickerVisible,
                      onConfirm = {newTime ->
                          viewModel.onEvent(AddTaskEvent.SetTargetTime(newTime))
                          viewModel.onEvent(AddTaskEvent.ToggleTimePicker(false))
                      },
                      value = state.targetTime,
                      onDismiss = {
                        viewModel.onEvent(AddTaskEvent.ToggleTimePicker(false))
                      }
        )
        DatePickerDialog(visible = state.datePickerVisible,
            onConfirm = {newDate ->
                            viewModel.onEvent(AddTaskEvent.SetStartDate(newDate))
                            viewModel.onEvent(AddTaskEvent.ToggleDatePicker(false))
                        },
            value = state.startDate,
            onDismiss = {
                viewModel.onEvent(AddTaskEvent.ToggleDatePicker(false))
                      },
        )


        PermissionChecker(onNotificationFail = {
            viewModel.onEvent(
                AddTaskEvent.SendError(
                    "You won't receive notifications, resulting in " +
                            "significantly decreased application functionality. ",
                )
            )
        }, onSchedulerFail = {
                viewModel.onEvent(
                    AddTaskEvent.SendError(
                        "Without access to the task scheduler, notifications for your " +
                                "tasks cannot be sent, significantly degrading application " +
                                "functionality.",

                        )
                )
            })

        AppError(errorMessage = state.error) {
            viewModel.onEvent(AddTaskEvent.DismissError)
        }

        KeyboardAdjust {
            Box(
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .imePadding()
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    NameField(
                        value = state.taskName,
                        onValueChange = {viewModel.onEvent(AddTaskEvent.SetName(it))},
                    )
                    DescriptionField(
                        value = state.taskDescription,
                        onValueChange = {viewModel.onEvent(AddTaskEvent.SetDescription(it))},
                    )

                    CategorySelector(categories = state.allCategories,
                                     onSelectCategory = {
                                         category ->
                                            viewModel.onEvent(AddTaskEvent.ToggleCategory(category))
                                    },
                                    selectedCategories = state.appliedCategories
                    )

                    VerticalDivider()

                    Row(Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Absolute.Left,
                        ) {

                        RepeatPeriodField(
                            value = (state.taskInterval ?: "").toString(),
                            onValueChange = {viewModel.onEvent(AddTaskEvent.SetInterval(it))},
                            enabled = !state.modifying,
                            failAction = {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "You can't modify the period of an existing task."
                                    )
                                }
                            }
                        )

                        Spacer(modifier = Modifier.width(15.dp))

                        TimeField(
                            value = String.format(
                                "%02d:%02d,",
                                state.targetTime.hour, state.targetTime.minute
                            ),
                            onClick = {viewModel.onEvent(AddTaskEvent.ToggleTimePicker(true))}
                        )
                    }


                    DateField(
                        value = state.startDate.format(
                            DateTimeFormatter.ofLocalizedDate(
                                FormatStyle.FULL
                            )
                        ),
                        onClick = {viewModel.onEvent(AddTaskEvent.ToggleDatePicker(true))},
                        enabled = !state.modifying,
                        failAction = {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "You can't modify the start date of an existing task."
                                )
                            }
                        }
                    )

                    NotificationField(
                        value = (state.notificationOffset ?: "").toString(),
                        onValueChange = {
                            viewModel.onEvent(AddTaskEvent.SetNotificationOffset(it))
                        }
                    )
                }
            }
        }
    }
}
