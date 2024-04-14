package com.example.p3project.domain.usecases

import com.example.p3project.domain.usecases.completions.CompleteTaskUseCase
import com.example.p3project.domain.usecases.completions.AllTaskCompletionsUseCase
import com.example.p3project.domain.usecases.notifications.ScheduleTaskUseCase
import com.example.p3project.domain.usecases.notifications.SendNotificationUseCase
import com.example.p3project.domain.usecases.tasks.AddTaskUseCase
import com.example.p3project.domain.usecases.tasks.GetTaskByIdUseCase
import com.example.p3project.domain.usecases.tasks.GetTasksUseCase


data class UseCases(
    val addTaskUseCase: AddTaskUseCase,

    val getTasksUseCase: GetTasksUseCase,
    val getTaskByIdUseCase: GetTaskByIdUseCase,

    val scheduleTaskUseCase: ScheduleTaskUseCase,
    val sendNotificationUseCase: SendNotificationUseCase,

    val completeTasksUseCase: CompleteTaskUseCase,
    val getTaskCompletionsUseCase: AllTaskCompletionsUseCase,
)
