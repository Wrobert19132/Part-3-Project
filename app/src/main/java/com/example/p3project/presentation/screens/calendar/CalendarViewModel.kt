package com.example.p3project.presentation.screens.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p3project.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    public val state = MutableStateFlow(
        CalendarState(listOf(),
            LocalDate.now()
        )
    )

    fun onEvent(event: CalendarEvent) {
        when (event) {
            CalendarEvent.ReloadInfo -> viewModelScope.launch(Dispatchers.IO) {
                reloadInfo()
            }

            is CalendarEvent.AdjustMonth -> adjustMonth(event.adjustBy)
        }
    }

    private fun adjustMonth(adjustBy: Int) {
        state.value = state.value.copy(
            currentDate = state.value.currentDate.plusMonths(adjustBy.toLong())
        )
    }

    private suspend fun reloadInfo() {
        state.value = state.value.copy(
            tasksInfo = useCases.getTasksUseCase(),
        )
    }
}