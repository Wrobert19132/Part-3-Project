package com.example.p3project.sources.presentation.screens.addtask

import com.example.p3project.sources.data.database.Task

sealed class AddTaskEvent {
    data class AddTask(val task: Task): AddTaskEvent()
    data object DismissError: AddTaskEvent()
}