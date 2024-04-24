package com.example.p3project.presentation.screens.calendar

import com.example.p3project.domain.model.TaskWithRelations
import java.time.LocalDate

data class CalendarState (
    var tasksInfo: List<TaskWithRelations>,
    var currentDate: LocalDate
)