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
        }
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