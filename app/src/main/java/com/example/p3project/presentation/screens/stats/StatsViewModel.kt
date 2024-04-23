package com.example.p3project.presentation.screens.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p3project.domain.usecases.UseCases
import com.example.p3project.domain.util.TaskViewMode
import com.example.p3project.presentation.screens.overview.OverviewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    val state = MutableStateFlow(
        StatsScreenState()
    )


    fun onEvent(event: StatsScreenEvent) {
        when (event) {
            is StatsScreenEvent.Reload -> {
                viewModelScope.launch(Dispatchers.IO) {
                    reloadData()
                }
            }
        }
    }


    suspend fun reloadData() {
        state.value = state.value.copy(
            dataLoaded = true,
            categoryUsage = useCases.buildCategoryUsageUseCase()
        )
    }
}