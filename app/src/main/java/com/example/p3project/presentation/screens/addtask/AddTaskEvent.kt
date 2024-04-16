package com.example.p3project.presentation.screens.addtask

import com.example.p3project.domain.model.Task
import java.time.LocalDate
import java.time.LocalTime

sealed class AddTaskEvent {
    data class AddTask(val name: String, val description: String,
                       val targetTime: LocalTime, val targetDate: LocalDate,
                       val notificationOffset: Int, val dayInterval: Int): AddTaskEvent()


    data class SendError(val message: String): AddTaskEvent()
    data object DismissError: AddTaskEvent()

}