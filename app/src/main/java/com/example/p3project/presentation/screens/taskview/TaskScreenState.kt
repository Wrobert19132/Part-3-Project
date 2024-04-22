package com.example.p3project.presentation.screens.taskview

import co.yml.charts.ui.piechart.models.PieChartData
import com.example.p3project.domain.model.Streak
import com.example.p3project.domain.model.TaskWithRelations

data class TaskScreenState(
    var taskInfo: TaskWithRelations? = null,

    var bestCompletionData: PieChartData? = null,
    var currentCompletionData: PieChartData? = null,

    var bestStreak: Streak? = null,
    var currentStreak: Streak? = null,

    var askDelete: Boolean = false
)