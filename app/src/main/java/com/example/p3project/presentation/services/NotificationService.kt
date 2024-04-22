package com.example.p3project.presentation.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.p3project.common.Constants
import com.example.p3project.domain.model.Task
import com.example.p3project.presentation.MainActivity

class NotificationService (private val context: Context) {
    fun taskNotification(task: Task) {

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(androidx.core.R.drawable.ic_call_decline)
            .setContentTitle(task.name)
            .setContentText(task.description)
            .setContentIntent(activityPendingIntent)
            .build()


        notificationManager.notify(task.taskId, notification)
    }

    fun followUpNotification(task: Task) {

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(androidx.core.R.drawable.ic_call_decline)
            .setContentTitle(task.name + " FOLLOWUP")
            .setContentText(task.description)
            .setContentIntent(activityPendingIntent)
            .build()


        notificationManager.notify(task.taskId, notification)
    }

}