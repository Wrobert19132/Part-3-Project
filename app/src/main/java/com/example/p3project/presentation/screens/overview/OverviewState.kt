package com.example.p3project.presentation.screens.overview

import com.example.p3project.domain.model.Category
import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.domain.util.TaskViewMode

data class OverviewState (
    val tasksInfo: List<TaskWithRelations>,
    val viewMode: TaskViewMode,

    val categories: List<Category>,
    val categoryFilters: Set<Int>,
)
