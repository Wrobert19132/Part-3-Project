package com.example.p3project.domain.usecases.notifications

import com.example.p3project.domain.service.InterruptScheduler
import com.example.p3project.domain.model.Task
import java.time.LocalDate
import java.time.LocalDateTime

class ScheduleTaskUseCase(private var  interruptScheduler: InterruptScheduler) {
    operator fun invoke(task: Task, now: LocalDateTime = LocalDateTime.now()) {
        val day = now.toLocalDate()

        var taskTime = task.nextTaskDateTime(day)

        if (now < taskTime) {
            interruptScheduler.scheduleFollowUpNotificationInterrupt(
                task,
                taskTime
            )
        } else {
            interruptScheduler.scheduleFollowUpNotificationInterrupt(
                task,
                task.nextTaskDateTime(day.plusDays(1))
            )
        }


        var notificationTime = taskTime.minusMinutes(task.notificationOffset.toLong())

        if (now < notificationTime) {
            interruptScheduler.scheduleTaskNotificationInterrupt(task,
                notificationTime
            )
        } else {
            interruptScheduler.scheduleTaskNotificationInterrupt(task,
                task.nextTaskDateTime(
                    day.plusDays(1)
                ).minusMinutes(task.notificationOffset.toLong())
            )
        }


    }
}