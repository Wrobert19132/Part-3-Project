package com.example.p3project.presentation.screens.addtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.usecases.UseCases
import com.example.p3project.domain.util.InvalidTaskException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AddTaskScreenViewmodel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {


    public val state = MutableStateFlow(
        AddTaskState()
    )

    private suspend fun addTask(name: String, description: String,
                                targetTime: LocalTime, targetDate: LocalDate,
                                notificationOffset: Int, dayInterval: Int) {
        try {
            val task = useCases.addTaskUseCase(name, description,
                                               targetTime, targetDate,
                                               notificationOffset, dayInterval
                                        )
            useCases.scheduleTaskUseCase(task)

            state.value = state.value.copy(taskAdded = true)

        } catch (e: InvalidTaskException) {
            onEvent(AddTaskEvent.SendError(e.message!!))
        }
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
                addTask(event.name, event.description,
                        event.targetTime, event.targetDate,
                        event.notificationOffset, event.dayInterval
                )
            }
        } else if (event is AddTaskEvent.SendError) {
            createError(event.message)
        } else if (event is AddTaskEvent.DismissError) {
            dismissError()
        }
    }
}
