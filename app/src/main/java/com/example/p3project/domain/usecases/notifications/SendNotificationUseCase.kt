package com.example.p3project.domain.usecases.notifications

import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.presentation.services.NotificationService
import java.time.LocalDateTime

class SendNotificationUseCase (val notificationService: NotificationService) {
    operator fun invoke(taskInfo: TaskWithRelations) {
        val task = taskInfo.task

        if (!task.enabled) {
            return
        }

        val now = LocalDateTime.now()

        if (taskInfo.completedToday(now.toLocalDate())) {
            return
        }


        val completionTime = task.nextTaskDateTime(now.toLocalDate()).minusMinutes(task.notificationOffset.toLong() / 2)

        if (now > completionTime) {
            return
        }

        notificationService.taskNotification(task)

    }
}