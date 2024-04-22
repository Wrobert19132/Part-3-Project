package com.example.p3project.domain.usecases.notifications

import com.example.p3project.common.Constants
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.service.InterruptScheduler
import java.time.LocalDateTime
import java.time.LocalTime

class ScheduleFollowUpNotificationUseCase(private var  interruptScheduler: InterruptScheduler) {
    operator fun invoke(task: Task) {
        val now = LocalDateTime.now()
        val warningTime =
            task.nextTaskDateTime(now.toLocalDate())
        println("Scheduling followup notif")

        if (warningTime > now) {
            interruptScheduler.scheduleFollowUpNotificationInterrupt(
                task,
                warningTime
            )
        }
    }
}