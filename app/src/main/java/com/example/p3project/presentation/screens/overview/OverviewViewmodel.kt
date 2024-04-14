package com.example.p3project.presentation.screens.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.usecases.UseCases
import com.example.p3project.domain.util.OverviewMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class OverviewViewmodel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    public val state = MutableStateFlow(
        OverviewState(listOf(), OverviewMode.TodayView)
    )

    fun onEvent(event: OverviewEvent) {
         if (event is OverviewEvent.ReloadTasks) {
            viewModelScope.launch(Dispatchers.IO) {
                getTasks()
            }
        } else if (event is OverviewEvent.CompleteTask) {
            viewModelScope.launch(Dispatchers.IO) {
                completeTask(event.task)
            }
        } else if (event is OverviewEvent.UpdateViewMode) {
            viewModelScope.launch(Dispatchers.IO) {
                updateViewMode(event.viewMode)
            }
         }
    }
    private suspend fun completeTask(task: Task) {

        useCases.completeTasksUseCase(task,
                                      task.periodsPassed(LocalDate.now()),
                                      LocalTime.now()
        )

        getTasks()
    }

    private suspend fun updateViewMode(mode: OverviewMode) {
        state.value = state.value.copy(viewMode = mode)
        getTasks()
    }

    private suspend fun getTasks() {
        state.value = state.value.copy(taskAndCompletions = useCases.getTaskCompletionsUseCase())
    }
}