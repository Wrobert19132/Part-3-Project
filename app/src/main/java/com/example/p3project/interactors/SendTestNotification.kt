package com.example.p3project.interactors

import android.content.Context
import com.example.p3project.sources.usecases.TestNotificationUseCase

class SendTestNotification(private val context: Context) {
    private val notificationService = TestNotificationUseCase(context)

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