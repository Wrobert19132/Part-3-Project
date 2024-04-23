package com.example.p3project.presentation.screens.stats

import co.yml.charts.ui.piechart.models.PieChartData
import com.example.p3project.domain.model.Completion

data class StatsScreenState (
    val dataLoaded: Boolean = false,
    val categoryUsage: PieChartData? = null,
)