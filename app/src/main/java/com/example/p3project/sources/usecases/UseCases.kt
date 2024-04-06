package com.example.p3project.sources.usecases


data class UseCases(
    val addTaskUseCase: AddTaskUseCase,

    val getTasksUseCase: GetTasksUseCase,
    val getTaskByIdUseCase: GetTaskByIdUseCase,

    val scheduleTaskUseCase: ScheduleTaskUseCase,
    val sendNotificationUseCase: SendNotificationUseCase

)
