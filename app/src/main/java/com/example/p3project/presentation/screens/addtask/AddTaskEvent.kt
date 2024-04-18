package com.example.p3project.presentation.screens.addtask

import com.example.p3project.domain.model.Category
import java.time.LocalDate
import java.time.LocalTime

sealed class AddTaskEvent {
    data object AddTask: AddTaskEvent()


    data class SendError(val message: String): AddTaskEvent()
    data object DismissError: AddTaskEvent()

    data object LoadCategories: AddTaskEvent()

    data class ToggleCategory(val category: Category): AddTaskEvent()
    data class SetName(val name: String): AddTaskEvent()
    data class SetDescription(val description: String): AddTaskEvent()
    data class SetInterval(val interval: String): AddTaskEvent()
    data class SetTargetTime(val targetTime: LocalTime): AddTaskEvent()
    data class SetStartDate(val startDate: LocalDate): AddTaskEvent()

    data class SetNotificationOffset(val offset: String): AddTaskEvent()

}