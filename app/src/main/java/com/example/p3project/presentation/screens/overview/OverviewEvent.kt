package com.example.p3project.presentation.screens.overview

import com.example.p3project.domain.model.Category
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.domain.util.TaskViewMode

sealed class OverviewEvent  {
    data class CompleteTask(val task: Task): OverviewEvent()
    data class UncompleteTask(val taskInfo: TaskWithRelations): OverviewEvent()
    data object ReloadInfo: OverviewEvent()
    data class ToggleCategory(val category: Category): OverviewEvent()
    data class UpdateViewMode(val viewMode: TaskViewMode): OverviewEvent()
}

