package com.example.p3project.domain.util

sealed class TaskViewMode {
    data object IncompleteView: TaskViewMode()
    data object TodayView: TaskViewMode()
    data object AllView: TaskViewMode()
}