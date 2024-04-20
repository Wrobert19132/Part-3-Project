package com.example.p3project.presentation.screens.addtask

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import com.example.p3project.domain.model.Category
import java.time.LocalDate
import java.time.LocalTime

data class AddTaskState(
    var datePickerVisible: Boolean = false,
    var timePickerVisible: Boolean = false,

    var error: String? = null,
    var taskAdded: Boolean = false,

    var allCategories: List<Category> = listOf(),

    var appliedCategories: Set<Int> = setOf(),
    var taskName: String = "",
    var taskDescription: String = "",
    var taskInterval: Int? = 7,
    var startDate: LocalDate = LocalDate.now(),
    var targetTime: LocalTime = LocalTime.now(),
    var notificationOffset: Int? = 30,

    )