package com.example.p3project.presentation.screens.taskview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p3project.domain.repository.TaskRepository
import com.example.p3project.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TaskScreenViewmodel @Inject constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle,
    private val useCases: UseCases
): ViewModel() {

    val id: Int = checkNotNull(savedStateHandle["taskId"])


    public val state = MutableStateFlow(
        TaskScreenState()
    )

    fun onEvent(event: TaskScreenEvent) {
        when (event) {
            is TaskScreenEvent.ReloadTask -> {
                viewModelScope.launch(Dispatchers.IO) {
                    reloadTask()
                }
            }

            is TaskScreenEvent.confirmDelete -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteTask()
                }

            }
            is TaskScreenEvent.toggleDeleteWarning -> {
                setDeleteConfirmVisibility(event.shown)

            }

            is TaskScreenEvent.CompleteTask -> {
                viewModelScope.launch(Dispatchers.IO) {
                    setTaskCompletion(event.complete)
                }
            }
        }
    }

    private suspend fun setTaskCompletion(complete: Boolean) {
        val now = LocalDateTime.now()
        val taskInfo = state.value.taskInfo!!
        val period = taskInfo.task.periodsPassed(taskInfo.task.nextTaskDay(LocalDate.now()))

        if (complete) {
            useCases.completeTasksUseCase(
                taskInfo.task,
                period,
                LocalDateTime.now()
            )
        } else {
            useCases.uncompleteTasksUseCase(
                taskInfo,
                period
            )
        }
        reloadTask()
    }

    private suspend fun deleteTask() {
        useCases.deleteTaskUseCase(state.value.taskInfo!!.task)
    }

    private fun setDeleteConfirmVisibility(visible: Boolean) {
        state.value = state.value.copy(askDelete = visible)
    }

    private suspend fun reloadTask() {
        state.value = state.value.copy(taskInfo = useCases.getTaskByIdUseCase(id))
    }

}