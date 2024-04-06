package com.example.p3project.sources.usecases

import android.content.BroadcastReceiver
import com.example.p3project.sources.data.InterruptScheduler
import com.example.p3project.sources.data.database.Task
import com.example.p3project.sources.repository.TaskRepository
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import javax.inject.Inject

class ScheduleTaskUseCase(private var  interruptScheduler: InterruptScheduler) {
    fun invoke(task: Task) {
        interruptScheduler.scheduleTaskInterrupt(task,
                                                 task.nextTaskDay(LocalDate.now()
                                                 )
        )
    }
}