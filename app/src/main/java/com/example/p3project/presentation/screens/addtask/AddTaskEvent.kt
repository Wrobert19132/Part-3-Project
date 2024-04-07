package com.example.p3project.presentation.screens.addtask

import com.example.p3project.domain.model.Task

sealed class AddTaskEvent {
    data class AddTask(val task: Task): AddTaskEvent()
    data class SendError(val message: String): AddTaskEvent()

    data object DismissError: AddTaskEvent()

}