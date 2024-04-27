package com.example.p3project.presentation.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.p3project.R
import com.example.p3project.common.Constants
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.service.NotificationService
import com.example.p3project.presentation.MainActivity

class NotificationServiceImpl (private val context: Context) : NotificationService {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun taskNotification(task: Task) {


        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )


        val notification = NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setContentTitle(task.name)
            .setContentText("It's time to complete your task \"${task.name}\"!")
            .setContentIntent(activityPendingIntent)

            .build()


        notificationManager.notify(task.taskId, notification)
    }

    override fun followUpNotification(task: Task) {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, Constants.FOLLOWUP_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setContentTitle(task.name)
            .setContentText("Your task \"${task.name}\" is due, and you still haven't completed it!")
            .setContentIntent(activityPendingIntent)
            .build()


        notificationManager.notify(task.taskId + 1000, notification)
    }

}