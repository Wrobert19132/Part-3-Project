package com.example.p3project.domain.usecases.notifications

import com.example.p3project.domain.service.InterruptScheduler
import com.example.p3project.domain.model.Task
import java.time.LocalDate
import java.time.LocalDateTime

class ScheduleTaskUseCase(private var  interruptScheduler: InterruptScheduler) {
    operator fun invoke(task: Task, from: LocalDate = LocalDate.now()) {
        val now = LocalDateTime.now()

        var taskTime = task.nextTaskDateTime(from)

        if (now < taskTime) {
            interruptScheduler.scheduleFollowUpNotificationInterrupt(
                task,
                taskTime
            )
        } else {
            interruptScheduler.scheduleFollowUpNotificationInterrupt(
                task,
                task.nextTaskDateTime(from.plusDays(1))
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
                    from.plusDays(1)
                ).minusMinutes(task.notificationOffset.toLong())
            )
        }


    }
}