package com.example.p3project.presentation.screens.taskview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.repository.TaskRepository
import com.example.p3project.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskScreenViewmodel @Inject constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle,
    private val useCases: UseCases
): ViewModel() {

    val id: Int = checkNotNull(savedStateHandle["taskId"])
    var task: Task? = null

    fun onEvent(event: TaskScreenEvent) {
        if (event is TaskScreenEvent.ReloadTask) {
            viewModelScope.launch(Dispatchers.IO) {
                reloadTask()
            }
        }
    }

    private suspend fun reloadTask() {
        task = useCases.getTaskByIdUseCase(id)
    }

}