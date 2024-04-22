package com.example.p3project;
import android.app.Application;
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.p3project.common.Constants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class P3App: Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val baseChannel = NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_ID,
                "Task Notifications", NotificationManager.IMPORTANCE_DEFAULT
                )

            val followupChannel = NotificationChannel(
                Constants.FOLLOWUP_CHANNEL_ID,
                "Warning Notifications", NotificationManager.IMPORTANCE_DEFAULT
            )

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(baseChannel)
            notificationManager.createNotificationChannel(followupChannel)
        }
    }
}
