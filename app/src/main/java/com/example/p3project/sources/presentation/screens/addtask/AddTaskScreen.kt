package com.example.p3project.sources.presentation.screens.addtask

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.p3project.sources.data.database.Task
import com.example.p3project.sources.presentation.screens.addtask.components.AppDatePicker
import com.example.p3project.sources.presentation.screens.addtask.components.AppTimePicker
import com.example.p3project.sources.presentation.screens.addtask.components.PermissionChecker
import com.example.p3project.sources.presentation.shared_components.AppError
import com.example.p3project.sources.presentation.shared_components.AppSnackbar
import com.example.p3project.sources.presentation.shared_components.KeyboardAdjust
import com.kotlinx.extend.isNumber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen (
    navController: NavController,
    viewModel: AddTaskScreenViewmodel = hiltViewModel()
) {

    val snackbarHostState = remember { SnackbarHostState() }


    val keyboardController = LocalSoftwareKeyboardController.current
    val state = viewModel.state.collectAsState().value

    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var dayInterval by remember { mutableStateOf("") }

    val now: OffsetDateTime = OffsetDateTime.now()

    val timePicker = rememberTimePickerState(now.hour, now.minute, false)
    val timePickerVisible = remember { mutableStateOf(false) }

    val datePicker = rememberDatePickerState(initialSelectedDateMillis = now.toEpochSecond() * 1000)
    val datePickerVisible = remember { mutableStateOf(false) }



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
        AppTimePicker(visible = timePickerVisible, state = timePicker)
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
                        value = taskName,
                        isError = (taskName.length >= Task.maxNameLength),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        onValueChange = {
                            taskName = if (it.length > Task.maxNameLength) {
                                taskName
                            } else {
                                it
                            }
                        },
                        singleLine = true,
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        label = {
                            Text(text = "Task Description")
                        },
                        value = taskDescription,
                        isError = (taskDescription.length >= Task.maxDescriptionLength),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        onValueChange = {
                            taskDescription = if (it.length > Task.maxDescriptionLength) {
                                taskDescription
                            } else {
                                it
                            }
                        }
                    )
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
                        value = dayInterval,
                        isError = (
                                if (dayInterval.isNumber()) {
                                    (dayInterval.toInt() > Task.maxDayInterval || dayInterval.toInt() <= 0)
                                } else {
                                    false
                                }
                                ),
                        onValueChange = {
                            dayInterval = if (it.isNumber()) { // Check for empty string
                                minOf(it.toInt(), 999).toString()
                            } else ""
                        }
                    )

                    val defaultCols = OutlinedTextFieldDefaults.colors()
                    OutlinedTextField(
                        enabled = false,
                        onValueChange = {},
                        modifier = Modifier.clickable { timePickerVisible.value = true },
                        value = String.format(
                            "Send notifications at: %02d:%02d",
                            timePicker.hour, timePicker.minute
                        ),
                        colors = defaultCols.copy(
                            disabledContainerColor = defaultCols.unfocusedContainerColor,
                            disabledTextColor = defaultCols.unfocusedTextColor,
                            disabledIndicatorColor = defaultCols.unfocusedIndicatorColor,
                            disabledPlaceholderColor = defaultCols.unfocusedPlaceholderColor
                        ),
                    )

                    val pickedDate: LocalDate = LocalDateTime.ofEpochSecond(
                        datePicker.selectedDateMillis?.div(1000)!!, 0, now.offset
                    ).toLocalDate()

                    OutlinedTextField(
                        enabled = false,
                        onValueChange = {},
                        modifier = Modifier.clickable { datePickerVisible.value = true },
                        value = "Starting: " + pickedDate.format(
                            DateTimeFormatter.ofLocalizedDate(
                                FormatStyle.FULL
                            )
                        ),
                        colors = defaultCols.copy(
                            disabledContainerColor = defaultCols.unfocusedContainerColor,
                            disabledTextColor = defaultCols.unfocusedTextColor,
                            disabledIndicatorColor = defaultCols.unfocusedIndicatorColor,
                            disabledPlaceholderColor = defaultCols.unfocusedPlaceholderColor
                        ),
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

                            if (dayInterval.isNumber()) {
                                viewModel.onEvent(
                                    AddTaskEvent.AddTask(
                                        Task(
                                            taskName, taskDescription,
                                            LocalTime.of(timePicker.hour, timePicker.minute),
                                            pickedDate, dayInterval.toInt()
                                        )
                                    )
                                )
                            } else {
                                viewModel.onEvent(
                                    AddTaskEvent.SendError(
                                        "You have to set an interval you want to complete tasks at!"
                                    )
                                )

                            }
                            navController.popBackStack()
                        }) {
                            Text(text = "Add")
                        }
                    }
                }
            }
        }
    }
}
