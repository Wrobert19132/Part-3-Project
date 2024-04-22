package com.example.p3project.domain.usecases.notifications

import com.example.p3project.domain.service.InterruptScheduler
import com.example.p3project.domain.model.Task
import java.time.LocalDate
import java.time.LocalDateTime

class ScheduleTaskUseCase(private var  interruptScheduler: InterruptScheduler) {
    operator fun invoke(task: Task, from: LocalDate = LocalDate.now()) {
        val now = LocalDateTime.now()

        var next = task.nextTaskDateTime(from).minusMinutes(task.notificationOffset.toLong())

        if (now < next) {
            interruptScheduler.scheduleTaskNotificationInterrupt(task,
                next
            )
        } else {
            var next = task.nextTaskDateTime(from.plusDays(1)).minusMinutes(task.notificationOffset.toLong())
            interruptScheduler.scheduleTaskNotificationInterrupt(task, next)

        }

    }
}