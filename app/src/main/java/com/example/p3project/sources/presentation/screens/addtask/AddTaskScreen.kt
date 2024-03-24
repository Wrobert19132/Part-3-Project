package com.example.p3project.sources.presentation.screens.addtask

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.p3project.sources.data.Task
import com.example.p3project.sources.presentation.screens.Screen
import com.example.p3project.sources.presentation.screens.overview.OverviewEvent
import com.example.p3project.sources.presentation.screens.overview.OverviewViewmodel
import com.example.p3project.sources.presentation.screens.overview.components.TaskCard
import com.example.p3project.sources.presentation.shared_components.AppSnackbar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen (
    navController: NavController,
    viewModel: OverviewViewmodel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value

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
        if (false) {
            Dialog({}) {
                Card() {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        TimePicker(
                            state = TimePickerState(12, 30, false),
                            layoutType = TimePickerLayoutType.Vertical,
                            modifier = Modifier.width(1000.dp)
                        )

                        Row(
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Absolute.Right
                        ) {
                            TextButton(onClick = { /*TODO*/ }) {
                                Text(text = "Cancel")
                            }
                            TextButton(onClick = { /*TODO*/ }) {
                                Text(text = "Set")
                            }
                        }
                    }
                }
            }
        }


        Box(Modifier.padding(paddingValues),
            contentAlignment = Alignment.Center) {
            Column (
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 25.dp)) {
                Spacer(modifier = Modifier.height(25.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label={
                        Text(text = "Task Name")
                    },
                    value = "",
                    onValueChange = {}
                )
                OutlinedTextField(
                    prefix = {
                        Text(text = "Repeat every ")
                    },
                    suffix = {
                             Text(text = " days")
                    },
                    value = "",
                    onValueChange = {}
                )



                Row(verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.Right) {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "Cancel")
                    }
                    TextButton(onClick = {
                        scope.launch { snackbarHostState.showSnackbar("Task Added!!")}
                        navController.popBackStack()
                    }) {
                        Text(text = "Add")
                    }
                }
            }
        }
    }
}
