package com.example.p3project.presentation.screens.calendar

import com.example.p3project.domain.model.Task
import com.example.p3project.presentation.screens.overview.OverviewEvent

sealed class CalendarEvent {
    data object ReloadInfo: CalendarEvent()
    data class AdjustMonth(val adjustBy: Int): CalendarEvent()

}