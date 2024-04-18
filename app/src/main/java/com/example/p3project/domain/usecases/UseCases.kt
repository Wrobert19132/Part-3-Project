package com.example.p3project.domain.usecases

import com.example.p3project.domain.usecases.categories.AllCategoriesUseCase
import com.example.p3project.domain.usecases.categories.CreateCategoryUseCase
import com.example.p3project.domain.usecases.categories.DeleteCategoryUseCase
import com.example.p3project.domain.usecases.completions.CompleteTaskUseCase
import com.example.p3project.domain.usecases.notifications.ScheduleTaskUseCase
import com.example.p3project.domain.usecases.notifications.SendNotificationUseCase
import com.example.p3project.domain.usecases.tasks.AddTaskUseCase
import com.example.p3project.domain.usecases.tasks.GetTaskInfoUseCase
import com.example.p3project.domain.usecases.tasks.AllTaskInfoUseCase


data class UseCases(
    val addTaskUseCase: AddTaskUseCase,

    val getTasksUseCase: AllTaskInfoUseCase,
    val getTaskByIdUseCase: GetTaskInfoUseCase,

    val scheduleTaskUseCase: ScheduleTaskUseCase,
    val sendNotificationUseCase: SendNotificationUseCase,

    val completeTasksUseCase: CompleteTaskUseCase,

    val createCategoryUseCase: CreateCategoryUseCase,
    val allCategoriesUseCase: AllCategoriesUseCase,
    val deleteCategoryUseCase: DeleteCategoryUseCase
)
