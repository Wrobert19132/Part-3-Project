package com.example.p3project.sources.presentation.screens.overview

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.p3project.sources.data.Task
import com.example.p3project.sources.data.TaskDatabase
import com.example.p3project.sources.data.TasksDao
import com.example.p3project.sources.presentation.screens.test.TestState
import com.example.p3project.sources.repository.TaskRepository
import com.example.p3project.sources.repository.TaskRepositoryImpl
import com.example.p3project.sources.usecases.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OverviewViewmodel @Inject constructor(
    private val taskRepository: TaskRepository
): ViewModel() {
    private val _state = mutableStateOf(
        OverviewState(listOf(Task("Example Task",
            0, "BLAH")))
    )

    val state: State<OverviewState> = _state
    val getTasksUseCase: GetTasksUseCase = GetTasksUseCase(taskRepository)
    init {
        //getNotes()
    }

    private fun getNotes() {


    }

    fun onEvent(event: OverviewEvent) {

    }
}