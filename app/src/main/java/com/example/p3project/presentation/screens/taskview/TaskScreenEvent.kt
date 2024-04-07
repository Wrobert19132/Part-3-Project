package com.example.p3project.presentation.screens.taskview

sealed class TaskScreenEvent  {
    data object ReloadTask: TaskScreenEvent()
}