package com.example.p3project.presentation.screens.overview

import com.example.p3project.domain.model.Task

sealed class OverviewEvent  {
    data class AddTask(val task: Task): OverviewEvent()
    data object ReloadTasks: OverviewEvent()

    data object GrantSchedulerPermission: OverviewEvent()

}