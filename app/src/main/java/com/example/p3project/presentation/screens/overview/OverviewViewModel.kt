package com.example.p3project.presentation.screens.overview

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.p3project.sources.services.SendNotificationService

class OverviewViewModel(application: Application): AndroidViewModel(application) {
    private val _state = mutableStateOf(OverviewState())
    val state: State<OverviewState> = _state

    private val sendNotification = SendNotificationService(application.applicationContext)

    fun sendNotification(notificationText: String) {
        sendNotification.send(notificationText)
        _state.value = OverviewState(
            _state.value.notifications_sent + 1
        )
    }
}

