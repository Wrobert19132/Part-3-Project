package com.example.p3project.sources.presentation.screens.test

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.p3project.interactors.SendTestNotification

class TestViewModel(application: Application): AndroidViewModel(application) {
    private val _state = mutableStateOf(TestState())
    val state: State<TestState> = _state
    var notificationInteractor = SendTestNotification(application.applicationContext)
    fun sendNotification(notificationText: String) {
        notificationInteractor(notificationText)
        _state.value = TestState(
            _state.value.notifications_sent + 1
        )
    }

    fun onPermissionResult(permission: Any, isGranted: Boolean) {

    }
}

