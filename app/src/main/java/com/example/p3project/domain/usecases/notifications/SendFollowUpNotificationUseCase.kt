package com.example.p3project.domain.usecases.notifications

import com.example.p3project.common.Constants
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.presentation.services.NotificationService
import java.time.LocalDateTime
import java.time.LocalTime

class SendFollowUpNotificationUseCase (private val notificationService: NotificationService) {
    operator fun invoke(taskInfo: TaskWithRelations) {
        val task = taskInfo.task

        if (!task.enabled) {
            return
        }

        val now = LocalDateTime.now()

        if (taskInfo.completedToday(now.toLocalDate())) {
            return
        }

        if (task.notificationOffset == 0) {
            return
        }
        println("Sending followup notification...")
        notificationService.followUpNotification(task)
    }
}