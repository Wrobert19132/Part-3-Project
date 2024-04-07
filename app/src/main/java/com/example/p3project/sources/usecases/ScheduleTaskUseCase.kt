package com.example.p3project.sources.usecases

import android.content.BroadcastReceiver
import com.example.p3project.sources.data.InterruptScheduler
import com.example.p3project.sources.data.database.Task
import com.example.p3project.sources.repository.TaskRepository
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import javax.inject.Inject

class ScheduleTaskUseCase(private var  interruptScheduler: InterruptScheduler) {
    operator fun invoke(task: Task, date: LocalDate = LocalDate.now()) {
        interruptScheduler.scheduleTaskInterrupt(task,
                                                 task.nextTaskDay(date)
        )
    }
}