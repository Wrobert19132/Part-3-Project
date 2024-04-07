package com.example.p3project.domain.usecases

import com.example.p3project.domain.repository.InterruptScheduler
import com.example.p3project.domain.model.Task
import java.time.LocalDate

class ScheduleTaskUseCase(private var  interruptScheduler: InterruptScheduler) {
    operator fun invoke(task: Task, date: LocalDate = LocalDate.now()) {
        interruptScheduler.scheduleTaskInterrupt(task,
                                                 task.nextTaskDay(date)
        )
    }
}