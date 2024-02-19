package com.example.p3project.presentation.overview

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.p3project.domain.use_case.SendNotificationUseCase

class OverviewViewModel: ViewModel() {
    private val _state = mutableStateOf(OverviewState())
    val state: State<OverviewState> = _state

    private val sendNotificationUseCase = SendNotificationUseCase()

    fun sendNotification(notificationText: String) {
        sendNotificationUseCase("PeePee")
        _state.value = OverviewState(
            _state.value.notifications_sent + 1
        )
    }
}

