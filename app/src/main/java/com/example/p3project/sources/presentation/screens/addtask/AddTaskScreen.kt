package com.example.p3project.sources.presentation.screens.addtask

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.p3project.sources.data.Task
import com.example.p3project.sources.presentation.screens.overview.OverviewEvent
import com.example.p3project.sources.presentation.shared_components.AppSnackbar
import com.kotlinx.extend.isNumber
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen (
    navController: NavController,
    viewModel: AddTaskScreenViewmodel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = viewModel.state.collectAsState().value

    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var dayInterval by remember { mutableIntStateOf(1) }

    val timePicker = rememberTimePickerState(12, 30, false);
    var timePickerVisible by remember { mutableStateOf(false) }


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
    ) {paddingValues ->
        if (timePickerVisible) {
            Dialog(onDismissRequest = {
                timePickerVisible = false
            }) {
                Card() {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        TimePicker(
                            state = timePicker,
                            layoutType = TimePickerLayoutType.Vertical,
                        )

                        Row(
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Absolute.Right
                        ) {
                            OutlinedButton(onClick = { timePickerVisible = false }) {
                                Text(text = "Set")
                            }
                        }
                    }
                }
            }
        }
        if (state.error != null) {
            AlertDialog(
                onDismissRequest = {viewModel.onEvent(AddTaskEvent.DismissError)},
                confirmButton = {
                    TextButton(
                        onClick = { (viewModel.onEvent(AddTaskEvent.DismissError)) }
                    ) { Text(text = "Okay") }
                },
                text = {Text(text = state.error!!)},
                tonalElevation = 50.dp
            )
        }


        Box(
            Modifier
                .padding(paddingValues)
                .fillMaxHeight(),
            ) {
            Column (
                Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                OutlinedTextField(

                    label={
                        Text(text = "Task Name")
                    },
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
                    label={
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
                        .width(220.dp),
                    prefix = {
                        Text(text = "Repeat every ")
                    },
                    suffix = {
                        Text(text = " days")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = dayInterval.toString(),
                    isError = (dayInterval > Task.maxDayInterval || dayInterval <= 0),
                    onValueChange = {
                        dayInterval = if (it.isNumber()) { // Check for empty string
                            val num = it.toInt()
                            minOf(it.toInt(), 999)
                        } else 1;
                    }
                )

            val defaultCols = OutlinedTextFieldDefaults.colors()
            OutlinedTextField(
                enabled = false,
                onValueChange = {},
                modifier = Modifier.clickable { timePickerVisible = true },
                value = String.format("Send notifications at: %02d:%02d",
                                      timePicker.hour, timePicker.minute),
                colors = defaultCols.copy(
                    disabledContainerColor = defaultCols.unfocusedContainerColor,
                    disabledTextColor = defaultCols.unfocusedTextColor,
                    disabledIndicatorColor = defaultCols.unfocusedIndicatorColor,
                    disabledPlaceholderColor = defaultCols.unfocusedPlaceholderColor
                    ),
                )




                Row(verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.Right) {
                    TextButton(onClick = { navController.popBackStack() }) {
                        Text(text = "Cancel")
                    }
                    Button(onClick = {
                        keyboardController?.hide()
                        viewModel.onEvent(
                            AddTaskEvent.AddTask(
                                Task(
                                    taskName, taskDescription,
                                    timePicker.hour, timePicker.minute, dayInterval
                                )
                            )
                        )
                        //navController.popBackStack()
                    }) {
                        Text(text = "Add")
                    }
                }
            }
        }
    }
}