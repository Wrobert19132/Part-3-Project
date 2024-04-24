package com.example.p3project.presentation.screens.taskview

sealed class TaskScreenEvent  {
    data object ReloadTask: TaskScreenEvent()
    data class CompleteTask(val complete: Boolean = true): TaskScreenEvent()
    data class ToggleDeleteWarning(val shown: Boolean): TaskScreenEvent()
    data class EnableTask(val enabled: Boolean): TaskScreenEvent()

    data object ConfirmDelete: TaskScreenEvent()

}