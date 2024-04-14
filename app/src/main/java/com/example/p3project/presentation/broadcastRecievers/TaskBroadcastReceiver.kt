package com.example.p3project.presentation.broadcastRecievers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.repository.InterruptScheduler
import com.example.p3project.domain.repository.TaskRepository
import com.example.p3project.domain.usecases.UseCases
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class TaskBroadcastReceiver: BroadcastReceiver() {

    @Inject lateinit var taskRepository: TaskRepository
    @Inject lateinit var taskScheduler: InterruptScheduler

    @Inject lateinit var useCases: UseCases

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(Dispatchers.IO).launch {
            val taskId: Int = intent?.getIntExtra("TASK_ID", -1)!!
            val task: Task? = taskRepository.getTask(taskId)

            if (task != null) {
                useCases.sendNotificationUseCase(context!!, task)

                useCases.scheduleTaskUseCase(task,
                                             task.nextTaskDay(
                                                 LocalDate.now().plusDays(1)
                                             )
                )
            }
        }
    }
}