package com.example.p3project.interactors

import android.content.Context
import com.example.p3project.sources.services.TestNotificationService

class SendTestNotification(private val context: Context) {
    private val notificationService = TestNotificationService(context)

    operator fun invoke(text: String) {
        if (text.isNotBlank()) {
            notificationService.send(text)
        } else {
            throw EmptyNotificationBody()
        }
    }

    class EmptyNotificationBody : Throwable() {

    }

}