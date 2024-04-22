package com.example.p3project.presentation.screens.stats

sealed class StatsScreenEvent {
    data object Reload : StatsScreenEvent()
}