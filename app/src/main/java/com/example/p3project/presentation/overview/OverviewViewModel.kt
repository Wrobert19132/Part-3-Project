package com.example.p3project.presentation.overview

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class OverviewViewModel: ViewModel() {
    private val _state = mutableStateOf(OverviewState(text2 = "Meow"))
    val state: State<OverviewState> = _state

    fun test() {
        _state.value = OverviewState(text = "Ding!")
    }
}