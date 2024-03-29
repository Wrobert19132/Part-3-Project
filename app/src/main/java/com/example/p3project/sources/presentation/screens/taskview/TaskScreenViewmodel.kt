package com.example.p3project.sources.presentation.screens.taskview

import androidx.lifecycle.ViewModel
import com.example.p3project.sources.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskScreenViewmodel @Inject constructor(
    private val taskRepository: TaskRepository
): ViewModel() {

}