package com.example.p3project.presentation.broadcastRecievers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.domain.usecases.UseCases
import com.example.p3project.domain.util.TaskViewMode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class BootBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var useCases: UseCases

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            CoroutineScope(Dispatchers.IO).launch {

                val taskInfos: List<TaskWithRelations> = useCases.getTasksUseCase(TaskViewMode.AllView, onlyEnabled = false)
                for (taskInfo in taskInfos) {
                    val task = taskInfo.task

                    useCases.scheduleTaskUseCase(task,
                        LocalDateTime.now()
                    )
                }
            }
        }
    }
}