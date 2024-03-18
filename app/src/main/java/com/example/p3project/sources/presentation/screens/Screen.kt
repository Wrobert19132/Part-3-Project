package com.example.p3project.sources.presentation.screens

sealed class Screen(val route: String) {
    data object OverviewScreen: Screen("overview")
    data object CalendarScreen: Screen("calendar")
    data object TestScreen: Screen("debug")
    data object AddtaskScreen: Screen("addtask")
}