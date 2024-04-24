package com.example.p3project.presentation.screens

sealed class Screen(val route: String) {
    data object OverviewScreen: Screen("overview")
    data object StatScreen: Screen("stats")
    data object ViewTaskScreen: Screen("viewtask")
    data object AddtaskScreen: Screen("addtask")

    data object CalendarScreen: Screen("calendar")
    data object SettingsScreen: Screen("settings")
}