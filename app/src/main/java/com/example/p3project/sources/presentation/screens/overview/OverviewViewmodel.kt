package com.example.p3project.sources.presentation.screens.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p3project.sources.repository.TaskRepository
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

    fun onEvent(event: OverviewEvent) {
        if (event is OverviewEvent.AddTask) {
            viewModelScope.launch(Dispatchers.IO) {
                addTaskUseCase(event.task)
                getTasks()
            }
        } else if (event is OverviewEvent.ReloadTasks) {
            viewModelScope.launch(Dispatchers.IO) {
                getTasks()
            }
        }
    }

    private suspend fun getTasks() {
        state.value = state.value.copy(tasks = getTasksUseCase())
    }
}