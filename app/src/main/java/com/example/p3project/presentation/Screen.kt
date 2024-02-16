package com.example.p3project.presentation

sealed class Screen(val route: String) {
    object OverviewScreen: Screen("overview")
}