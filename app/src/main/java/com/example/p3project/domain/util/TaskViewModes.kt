package com.example.p3project.domain.util

sealed class OverviewMode {
    data object TodayView: OverviewMode()
    data object AllView: OverviewMode()
}