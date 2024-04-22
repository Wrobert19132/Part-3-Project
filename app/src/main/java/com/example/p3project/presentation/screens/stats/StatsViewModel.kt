package com.example.p3project.presentation.screens.stats

import androidx.lifecycle.ViewModel
import com.example.p3project.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

}