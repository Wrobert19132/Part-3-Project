package com.example.p3project.presentation

sealed class Screen(val route: String) {
    data object OverviewScreen: Screen("overview")
    data object CalendarScreen: Screen("calendar")
}