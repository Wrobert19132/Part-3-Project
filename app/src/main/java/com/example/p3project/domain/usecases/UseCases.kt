package com.example.p3project.domain.usecases

import com.example.p3project.domain.usecases.categories.AllCategoriesUseCase
import com.example.p3project.domain.usecases.categories.AssignCategoryUseCase
import com.example.p3project.domain.usecases.categories.CreateCategoryUseCase
import com.example.p3project.domain.usecases.categories.DeleteCategoryUseCase
import com.example.p3project.domain.usecases.categories.UnassignCategoryUseCase
import com.example.p3project.domain.usecases.charts.BuildCategoryUsageUseCase
import com.example.p3project.domain.usecases.charts.BuildCompletionDataUseCase
import com.example.p3project.domain.usecases.completions.CompleteTaskUseCase
import com.example.p3project.domain.usecases.completions.UncompleteTaskUseCase
import com.example.p3project.domain.usecases.notifications.ScheduleFollowUpNotificationUseCase
import com.example.p3project.domain.usecases.notifications.ScheduleTaskUseCase
import com.example.p3project.domain.usecases.notifications.SendFollowUpNotificationUseCase
import com.example.p3project.domain.usecases.notifications.SendNotificationUseCase
import com.example.p3project.domain.usecases.tasks.AddTaskUseCase
import com.example.p3project.domain.usecases.tasks.GetTaskUseCase
import com.example.p3project.domain.usecases.tasks.GetTasksUseCase
import com.example.p3project.domain.usecases.tasks.DeleteTaskUseCase
import com.example.p3project.domain.usecases.tasks.ModifyTaskUseCase
import com.example.p3project.domain.usecases.tasks.SetTaskEnabledUseCase


data class UseCases(
    val addTaskUseCase: AddTaskUseCase,
    val deleteTaskUseCase: DeleteTaskUseCase,
    val modifyTaskUseCase: ModifyTaskUseCase,
    val setTaskEnabledUseCase: SetTaskEnabledUseCase,

    val getTasksUseCase: GetTasksUseCase,
    val getTaskByIdUseCase: GetTaskUseCase,

    val scheduleFollowUpNotificationUseCase: ScheduleFollowUpNotificationUseCase,
    val scheduleTaskUseCase: ScheduleTaskUseCase,

    val sendFollowUpNotificationUseCase: SendFollowUpNotificationUseCase,
    val sendNotificationUseCase: SendNotificationUseCase,


    val completeTasksUseCase: CompleteTaskUseCase,
    val uncompleteTasksUseCase: UncompleteTaskUseCase,

    val createCategoryUseCase: CreateCategoryUseCase,
    val allCategoriesUseCase: AllCategoriesUseCase,
    val deleteCategoryUseCase: DeleteCategoryUseCase,
    val assignCategoryUseCase: AssignCategoryUseCase,
    val unassignCategoryUseCase: UnassignCategoryUseCase,

    val buildCompletionChartUseCase: BuildCompletionDataUseCase,
    val buildCategoryUsageUseCase: BuildCategoryUsageUseCase


)
