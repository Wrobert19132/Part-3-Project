package com.example.p3project.presentation.screens.addtask

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.p3project.domain.model.Task
import com.example.p3project.presentation.screens.addtask.components.AppDatePicker
import com.example.p3project.presentation.screens.addtask.components.AppTimePicker
import com.example.p3project.presentation.screens.addtask.components.CategorySelector
import com.example.p3project.presentation.screens.addtask.components.ClickableTextField
import com.example.p3project.presentation.screens.addtask.components.PermissionChecker
import com.example.p3project.presentation.screens.shared_components.AppError
import com.example.p3project.presentation.screens.shared_components.AppSnackbar
import com.example.p3project.presentation.screens.shared_components.KeyboardAdjust
import com.kotlinx.extend.isEmptyOrInt
import com.kotlinx.extend.isInt
import com.kotlinx.extend.isNumber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen (
    navController: NavController,
    taskId: Int?,
    viewModel: AddTaskScreenViewmodel = hiltViewModel(),
) {

    val snackbarHostState = remember { SnackbarHostState() }


    val keyboardController = LocalSoftwareKeyboardController.current
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(state.taskAdded) {
        if (state.taskAdded) {
            navController.popBackStack()
        }
    }

    LaunchedEffect(true) {
        viewModel.onEvent(AddTaskEvent.LoadCategories)
    }



    val now: OffsetDateTime = OffsetDateTime.now()

    val notificationTimePicker = rememberTimePickerState(now.hour, now.minute, false)
    val notificationTimePickerVisible = remember { mutableStateOf(false) }
    val pickedTime = LocalTime.of(notificationTimePicker.hour, notificationTimePicker.minute)

    val datePicker = rememberDatePickerState(initialSelectedDateMillis = now.toEpochSecond() * 1000)
    val datePickerVisible = remember { mutableStateOf(false) }
    val pickedDate: LocalDate = LocalDateTime.ofEpochSecond(
        datePicker.selectedDateMillis?.div(1000)!!, 0, now.offset
    ).toLocalDate()


    LaunchedEffect(notificationTimePicker) {
        viewModel.onEvent(AddTaskEvent.SetTargetTime(pickedTime))
    }

    LaunchedEffect(datePicker) {
        viewModel.onEvent(AddTaskEvent.SetTargetTime(pickedTime))
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
                    Text("Add a Task")
                },
            )
        },
    ) { paddingValues ->
        AppTimePicker(visible = notificationTimePickerVisible, state = notificationTimePicker)
        AppDatePicker(visible = datePickerVisible, state = datePicker)


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
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = "Task Name")
                        },
                        leadingIcon = { Icon(Icons.Default.Create, "Set Task Name") },
                        value = state.taskName,
                        isError = (state.taskName.length >= Task.maxNameLength),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        onValueChange = {viewModel.onEvent(AddTaskEvent.SetName(it))},
                        singleLine = true,
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        label = {
                            Text(text = "Task Description")
                        },
                        value = state.taskDescription,
                        isError = (state.taskDescription.length >= Task.maxDescriptionLength),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
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
                        OutlinedTextField(
                            modifier = Modifier
                                .width(210.dp),
                            prefix = {
                                Text(text = "Repeat every ")
                            },
                            suffix = {
                                Text(text = " days")
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            value = (state.taskInterval ?: "").toString(),
                            onValueChange = {viewModel.onEvent(AddTaskEvent.SetInterval(it))
                            }
                        )

                        Spacer(modifier = Modifier.width(15.dp))
                        
                        ClickableTextField(
                            value = String.format(
                                "at: %02d:%02d,",
                                notificationTimePicker.hour, notificationTimePicker.minute
                            ),
                            Modifier.width(110.dp),
                            onClick = { notificationTimePickerVisible.value = true }
                        )
                    }


                    ClickableTextField(
                        value = "Starting on " + pickedDate.format(
                            DateTimeFormatter.ofLocalizedDate(
                                FormatStyle.FULL
                            )
                        ),
                        onClick = {datePickerVisible.value = true}
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        prefix = {
                            Text(text = "Send notification ")
                        },
                        suffix = {
                            Text(text = " minutes before target")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        value = (state.notificationOffset ?: "").toString(),
                        onValueChange = {
                            viewModel.onEvent(AddTaskEvent.SetNotificationOffset(it))
                        }
                    )



                    Row(
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Absolute.Right
                    ) {
                        TextButton(onClick = { navController.popBackStack() }) {
                            Text(text = "Cancel")
                        }
                        Button(onClick = {
                            keyboardController?.hide()
                            viewModel.onEvent(AddTaskEvent.AddTask)
                            }
                        ) {
                            Text(text = "Add")
                        }
                    }
                }
            }
        }
    }
}
