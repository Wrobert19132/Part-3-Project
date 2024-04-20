package com.example.p3project.presentation.screens.taskview

sealed class TaskScreenEvent  {
    data object ReloadTask: TaskScreenEvent()
    data class CompleteTask(val complete: Boolean = true): TaskScreenEvent()
    data class toggleDeleteWarning(val shown: Boolean): TaskScreenEvent()
    data object confirmDelete: TaskScreenEvent()

}