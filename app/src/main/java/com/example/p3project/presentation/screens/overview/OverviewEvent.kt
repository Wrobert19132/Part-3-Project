package com.example.p3project.presentation.screens.overview

import com.example.p3project.domain.model.Task
import com.example.p3project.domain.util.TaskViewMode

sealed class OverviewEvent  {
    data class CompleteTask(val task: Task): OverviewEvent()
    data object ReloadTasks: OverviewEvent()

    data class UpdateViewMode(val viewMode: TaskViewMode): OverviewEvent()
}