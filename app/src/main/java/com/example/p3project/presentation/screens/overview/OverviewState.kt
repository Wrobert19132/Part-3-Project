package com.example.p3project.presentation.screens.overview

import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.domain.util.TaskViewMode

data class OverviewState (
    var taskAndCompletions: List<TaskWithRelations>,
    var viewMode: TaskViewMode
)