package com.example.p3project;
import android.app.Application;
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.p3project.common.Constants

class P3App: Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(Constants.NOTIFICATION_CHANNEL_ID,
                "Notification",
                NotificationManager.IMPORTANCE_DEFAULT
                )
        }

    }
}
