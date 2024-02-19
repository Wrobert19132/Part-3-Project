package com.example.p3project.presentation.overview

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class OverviewViewModel: ViewModel() {
    private val _state = mutableStateOf(OverviewState())
    val state: State<OverviewState> = _state

    fun test() {
        _state.value = OverviewState(_state.value.notifications_sent + 1)
    }
}