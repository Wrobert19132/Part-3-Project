package com.example.p3project.services

import com.example.p3project.domain.model.Task
import com.example.p3project.domain.service.InterruptScheduler
import com.example.p3project.domain.service.NotificationService
import java.time.LocalDateTime

class TestNotificationService: NotificationService {
    public val sentNotifications: MutableList<Task> = mutableListOf()
    public val sentFollowupNotifications: MutableList<Task> = mutableListOf()

    override fun taskNotification(task: Task) {
        sentNotifications.add(task)
    }

    override fun followUpNotification(task: Task) {
        sentFollowupNotifications.add(task)
    }

}