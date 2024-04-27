package com.example.p3project.domain.service

import com.example.p3project.domain.model.Task
import java.time.LocalDateTime

interface NotificationService {
    fun taskNotification(task: Task)
    fun followUpNotification(task: Task)
}