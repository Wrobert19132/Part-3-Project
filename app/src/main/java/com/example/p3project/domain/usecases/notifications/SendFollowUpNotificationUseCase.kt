package com.example.p3project.domain.usecases.notifications

import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.presentation.services.NotificationServiceImpl
import java.time.LocalDateTime

class SendFollowUpNotificationUseCase (private val notificationService: NotificationServiceImpl) {
    operator fun invoke(taskInfo: TaskWithRelations) {
        val task = taskInfo.task

        if (!task.enabled) {
            return
        }

        val now = LocalDateTime.now()

        if (taskInfo.completedOnDay(now.toLocalDate())) {
            return
        }

        if (task.notificationOffset == 0) {
            return
        }

        notificationService.followUpNotification(task)

    }
}