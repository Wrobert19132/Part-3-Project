package com.example.p3project.domain.usecases.notifications

import com.example.p3project.domain.service.InterruptScheduler
import com.example.p3project.domain.model.Task
import java.time.LocalDate

class ScheduleTaskUseCase(private var  interruptScheduler: InterruptScheduler) {
    operator fun invoke(task: Task, from: LocalDate = LocalDate.now()) {
        interruptScheduler.scheduleTaskNotificationInterrupt(task,
                                                 task.nextTaskDay(from)
        )
    }
}