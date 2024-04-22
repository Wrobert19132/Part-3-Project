package com.example.p3project.domain.usecases.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.p3project.common.Constants
import com.example.p3project.domain.model.Task
import com.example.p3project.presentation.MainActivity
import com.example.p3project.presentation.services.NotificationService
import java.time.LocalTime

class SendNotificationUseCase (val notificationService: NotificationService) {
    operator fun invoke(task: Task) {
        if (task.enabled) {
            val now = LocalTime.now()
            if (now > task.targetTime.minusMinutes(task.notificationOffset.toLong() / 2)) {
                return
            }
            notificationService(task)
        }
    }
}