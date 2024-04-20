package com.example.p3project.presentation.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.repository.InterruptScheduler
import com.example.p3project.presentation.broadcastRecievers.TaskNotificationBroadcastReceiver
import java.time.LocalDate
import java.time.OffsetDateTime

class InterruptSchedulerService(private val context: Context) : InterruptScheduler {
    private var alarmManager = context.getSystemService(AlarmManager::class.java)

    @Throws(SecurityException::class)
    override fun scheduleTaskNotificationInterrupt(task: Task, date: LocalDate) {
        val intent = Intent(context, TaskNotificationBroadcastReceiver::class.java).apply {
            putExtra("TASK_ID", task.taskId)
        }
        val now = OffsetDateTime.now()

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,

            task.nextNotificationDateTime(date).toEpochSecond(now.offset)*1000,
            PendingIntent.getBroadcast(context,
                task.taskId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancelTaskNotificationInterrupt(task: Task) {
        alarmManager.cancel(PendingIntent.getBroadcast(
            context,
            task.taskId,
            Intent(context, TaskNotificationBroadcastReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        ))
    }

}