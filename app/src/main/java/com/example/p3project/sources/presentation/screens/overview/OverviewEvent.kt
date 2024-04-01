package com.example.p3project.sources.presentation.screens.overview

import com.example.p3project.sources.data.database.Task

sealed class OverviewEvent  {
    data class AddTask(val task: Task): OverviewEvent()
    data object ReloadTasks: OverviewEvent()

}