package com.example.p3project.presentation.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.service.InterruptScheduler
import com.example.p3project.presentation.broadcastRecievers.TaskNotificationBroadcastReceiver
import com.example.p3project.presentation.broadcastRecievers.TaskWarningNotificationBroadcastReceiver
import java.time.LocalDateTime
import java.time.OffsetDateTime

class InterruptSchedulerImpl(private val context: Context) : InterruptScheduler {
    private var alarmManager = context.getSystemService(AlarmManager::class.java)

    @Throws(SecurityException::class)
    override fun scheduleTaskNotificationInterrupt(task: Task, date: LocalDateTime) {
        println("SCHEDULED ${task.name} FOR {$date}")
        val intent = Intent(context, TaskNotificationBroadcastReceiver::class.java).apply {
            putExtra("TASK_ID", task.taskId)
        }
        val now = OffsetDateTime.now()

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,

            date.toEpochSecond(now.offset)*1000,
            PendingIntent.getBroadcast(context,
                1000 + task.taskId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    @Throws(SecurityException::class)
    override fun scheduleFollowUpNotificationInterrupt(task: Task, date: LocalDateTime) {
        val intent = Intent(context, TaskWarningNotificationBroadcastReceiver::class.java).apply {
            putExtra("TASK_ID", task.taskId)
        }

        println("SCHEDULED FOLLOWUP ${task.name} FOR {$date}")

        val now = OffsetDateTime.now()

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,

            date.toEpochSecond(now.offset)*1000,
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