package com.example.p3project.domain.usecases.notifications

import com.example.p3project.domain.model.Task
import com.example.p3project.domain.service.InterruptScheduler
import java.time.LocalTime

class ScheduleFollowUpNotificationUseCase(private var  interruptScheduler: InterruptScheduler) {
    operator fun invoke(task: Task) {
        val now = LocalTime.now()
        val warningTime = task.targetTime.minusMinutes(10)

        if (now < warningTime) {

        }
    }
}