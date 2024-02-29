package com.example.p3project.sources.presentation.screens.overview

import android.app.Application
import androidx.compose.material3.Snackbar
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.AndroidViewModel
import com.example.p3project.interactors.SendTestNotification
import com.example.p3project.sources.services.TestNotificationService

class OverviewViewModel(application: Application): AndroidViewModel(application) {
    private val _state = mutableStateOf(OverviewState())
    val state: State<OverviewState> = _state
    var notificationInteractor = SendTestNotification(application.applicationContext)
    fun sendNotification(notificationText: String) {
        notificationInteractor(notificationText)
        _state.value = OverviewState(
            _state.value.notifications_sent + 1
        )
    }

    fun onPermissionResult(permission: Any, isGranted: Boolean) {

    }
}

