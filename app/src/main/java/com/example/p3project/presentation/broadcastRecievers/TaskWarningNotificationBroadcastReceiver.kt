package com.example.p3project.presentation.broadcastRecievers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.domain.service.InterruptScheduler
import com.example.p3project.domain.repository.TaskRepository
import com.example.p3project.domain.usecases.UseCases
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class TaskWarningNotificationBroadcastReceiver: BroadcastReceiver() {

    @Inject lateinit var taskRepository: TaskRepository
    @Inject lateinit var useCases: UseCases


    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(Dispatchers.IO).launch {

            val taskId: Int = intent?.getIntExtra("TASK_ID", -1)!!
            val taskInfo: TaskWithRelations? = taskRepository.getTaskInfo(taskId)
            println("Received Warning Notification")

            if (taskInfo != null) {
                useCases.sendFollowUpNotificationUseCase(taskInfo)

                val task = taskInfo.task
                useCases.scheduleTaskUseCase(task,
                    LocalDateTime.now()
                )

            }

        }
    }
}