package com.example.p3project.presentation.screens.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p3project.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class OverviewViewmodel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    public val state = MutableStateFlow(
        OverviewState(listOf())
    )

    fun onEvent(event: OverviewEvent) {
         if (event is OverviewEvent.ReloadTasks) {
            viewModelScope.launch(Dispatchers.IO) {
                getTasks()
            }
        }
    }

    private suspend fun getTasks() {
        state.value = state.value.copy(tasks = useCases.getTasksUseCase())
    }
}