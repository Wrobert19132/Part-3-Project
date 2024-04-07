package com.example.p3project.presentation.screens.addtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskScreenViewmodel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {


    public val state = MutableStateFlow(
        AddTaskState()
    )

    private suspend fun addTask(task: Task) {
        useCases.addTaskUseCase(task)
        useCases.scheduleTaskUseCase(task)
    }

    private fun dismissError() {
        state.value = state.value.copy(error=null)
    }

    private fun createError(error: String) {
        state.value = state.value.copy(error=error)
    }

    fun onEvent(event: AddTaskEvent) {
        if (event is AddTaskEvent.AddTask) {
            viewModelScope.launch(Dispatchers.IO) {
                addTask(event.task)
            }

        } else if (event is AddTaskEvent.SendError) {
            createError(event.message)


        } else if (event is AddTaskEvent.DismissError) {
            dismissError()
        }
    }
}
