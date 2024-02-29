package com.example.p3project.sources.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.p3project.common.Constants
import com.example.p3project.presentation.MainActivity

class SendNotificationService (
    private val context: Context) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun send(notificationText: String) {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(androidx.core.R.drawable.ic_call_decline)
            .setContentTitle("Test Notification")
            .setContentText(notificationText)
            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(1, notification)
    }
}
