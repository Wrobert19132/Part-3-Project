package com.example.p3project.presentation.screens.overview

import com.example.p3project.domain.model.Category
import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.domain.util.TaskViewMode

data class OverviewState (
    var tasksInfo: List<TaskWithRelations>,
    var viewMode: TaskViewMode,

    var categories: List<Category> = listOf(),
    var categoryFilters: Set<Int> = setOf(),
)