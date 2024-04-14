package com.example.p3project.presentation.screens.overview

import com.example.p3project.domain.model.Task
import com.example.p3project.domain.model.TaskWithCompletions
import com.example.p3project.domain.util.OverviewMode

data class OverviewState (
    var taskAndCompletions: List<TaskWithCompletions>,
    var viewMode: OverviewMode
)