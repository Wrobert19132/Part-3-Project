package com.example.p3project.domain.usecases

import com.example.p3project.domain.usecases.categories.AllCategoriesUseCase
import com.example.p3project.domain.usecases.categories.AssignCategoryUseCase
import com.example.p3project.domain.usecases.categories.CreateCategoryUseCase
import com.example.p3project.domain.usecases.categories.DeleteCategoryUseCase
import com.example.p3project.domain.usecases.categories.UnassignCategoryUseCase
import com.example.p3project.domain.usecases.completions.CompleteTaskUseCase
import com.example.p3project.domain.usecases.completions.UncompleteTaskUseCase
import com.example.p3project.domain.usecases.notifications.ScheduleFollowUpNotificationUseCase
import com.example.p3project.domain.usecases.notifications.ScheduleTaskUseCase
import com.example.p3project.domain.usecases.notifications.SendNotificationUseCase
import com.example.p3project.domain.usecases.tasks.AddTaskUseCase
import com.example.p3project.domain.usecases.tasks.GetTaskInfoUseCase
import com.example.p3project.domain.usecases.tasks.AllTaskInfoUseCase
import com.example.p3project.domain.usecases.tasks.DeleteTaskUseCase
import com.example.p3project.domain.usecases.tasks.ModifyTaskUseCase


data class UseCases(
    val addTaskUseCase: AddTaskUseCase,
    val deleteTaskUseCase: DeleteTaskUseCase,
    val modifyTaskUseCase: ModifyTaskUseCase,

    val getTasksUseCase: AllTaskInfoUseCase,
    val getTaskByIdUseCase: GetTaskInfoUseCase,

    val scheduleTaskUseCase: ScheduleTaskUseCase,
    val sendNotificationUseCase: SendNotificationUseCase,
    val scheduleFollowUpNotificationUseCase: ScheduleFollowUpNotificationUseCase,

    val completeTasksUseCase: CompleteTaskUseCase,
    val uncompleteTasksUseCase: UncompleteTaskUseCase,

    val createCategoryUseCase: CreateCategoryUseCase,
    val allCategoriesUseCase: AllCategoriesUseCase,
    val deleteCategoryUseCase: DeleteCategoryUseCase,
    val assignCategoryUseCase: AssignCategoryUseCase,
    val unassignCategoryUseCase: UnassignCategoryUseCase

)
