package com.example.p3project.presentation.screens.taskview

import com.example.p3project.domain.model.TaskWithRelations

data class TaskScreenState(
    var taskInfo: TaskWithRelations? = null,
    var askDelete: Boolean = false
)