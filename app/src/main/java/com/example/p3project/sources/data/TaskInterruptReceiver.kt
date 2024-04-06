package com.example.p3project.sources.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.p3project.sources.data.database.Task
import com.example.p3project.sources.data.database.TaskDatabase
import com.example.p3project.sources.repository.TaskRepository
import com.example.p3project.sources.usecases.SendNotificationUseCase
import com.example.p3project.sources.usecases.TestNotificationUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class TaskInterruptReceiver: BroadcastReceiver() {

    @Inject lateinit var taskRepository: TaskRepository
    @Inject lateinit var taskScheduler: InterruptScheduler

    private val notificationUseCase = SendNotificationUseCase()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(Dispatchers.IO).launch {
            val taskId: Int = intent?.getIntExtra("TASK_ID", -1)!!
            val task: Task? = taskRepository.getTask(taskId)

            if (task != null) {
                notificationUseCase(context!!, task)

                taskScheduler.scheduleTaskInterrupt(task,
                                                    task.nextTaskDay(
                                                        LocalDate.now().plusDays(1)
                                                    )
                )
            }
        }
    }
}