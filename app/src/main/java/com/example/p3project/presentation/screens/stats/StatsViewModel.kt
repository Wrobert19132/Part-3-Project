package com.example.p3project.presentation.screens.stats

import androidx.lifecycle.ViewModel
import com.example.p3project.domain.usecases.UseCases
import com.example.p3project.domain.util.TaskViewMode
import com.example.p3project.presentation.screens.overview.OverviewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    val state = MutableStateFlow(
        OverviewState(listOf(), TaskViewMode.IncompleteView)
    )


    fun onEvent(event: StatsScreenEvent) {
        when (event) {
            is StatsScreenEvent.Reload -> {

            }
        }
    }
}