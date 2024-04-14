package com.example.p3project.domain.usecases

import com.example.p3project.domain.usecases.completions.CompleteTaskUseCase
import com.example.p3project.domain.usecases.notifications.ScheduleTaskUseCase
import com.example.p3project.domain.usecases.notifications.SendNotificationUseCase
import com.example.p3project.domain.usecases.tasks.AddTaskUseCase
import com.example.p3project.domain.usecases.tasks.GetTaskInfoByIdUseCase
import com.example.p3project.domain.usecases.tasks.AllTaskInfoUseCase


data class UseCases(
    val addTaskUseCase: AddTaskUseCase,

    val getTasksUseCase: AllTaskInfoUseCase,
    val getTaskByIdUseCase: GetTaskInfoByIdUseCase,

    val scheduleTaskUseCase: ScheduleTaskUseCase,
    val sendNotificationUseCase: SendNotificationUseCase,

    val completeTasksUseCase: CompleteTaskUseCase,
)
