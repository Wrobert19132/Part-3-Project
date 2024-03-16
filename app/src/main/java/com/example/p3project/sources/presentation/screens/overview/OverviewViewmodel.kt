package com.example.p3project.sources.presentation.screens.overview

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p3project.sources.data.Task
import com.example.p3project.sources.data.TaskDatabase
import com.example.p3project.sources.data.TasksDao
import com.example.p3project.sources.presentation.screens.test.TestState
import com.example.p3project.sources.repository.TaskRepository
import com.example.p3project.sources.repository.TaskRepositoryImpl
import com.example.p3project.sources.usecases.AddTaskUseCase
import com.example.p3project.sources.usecases.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class OverviewViewmodel @Inject constructor(
    private val taskRepository: TaskRepository
): ViewModel() {

    public val state = MutableStateFlow(
        OverviewState(listOf())
    )

    val getTasksUseCase: GetTasksUseCase = GetTasksUseCase(taskRepository)
    val addTaskUseCase: AddTaskUseCase = AddTaskUseCase(taskRepository)
    init {
        getNotes()
    }

    fun onEvent(event: OverviewEvent) {
        if (event is OverviewEvent.AddTask) {
            viewModelScope.launch(Dispatchers.IO) {
                addTaskUseCase(event.task)
                getNotes()
            }
        }
    }

    private fun getNotes() {
        viewModelScope.launch (Dispatchers.IO){
            state.value = state.value.copy(tasks = getTasksUseCase())
        }

    }
}