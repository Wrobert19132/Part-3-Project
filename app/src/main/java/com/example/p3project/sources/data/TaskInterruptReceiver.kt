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
import javax.inject.Inject

@AndroidEntryPoint
class TaskInterruptReceiver: BroadcastReceiver() {
    @Inject lateinit var taskRepository: TaskRepository
    private val notificationUseCase = SendNotificationUseCase()

    override fun onReceive(context: Context?, intent: Intent?) {
        val task: Task? =  taskRepository.getTask(5)
        if (task != null) {
            notificationUseCase(context!!, task)
        }
    }
}