package com.example.p3project.sources.presentation.screens.taskview

sealed class TaskScreenEvent  {
    data object ReloadTask: TaskScreenEvent()
}