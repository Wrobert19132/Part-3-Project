package com.example.p3project.data.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.repository.InterruptScheduler
import com.example.p3project.data.broadcastRecievers.TaskBroadcastReceiver
import java.time.LocalDate
import java.time.OffsetDateTime

class InterruptSchedulerImpl(private val context: Context) : InterruptScheduler {
    private var alarmManager = context.getSystemService(AlarmManager::class.java)

    @Throws(SecurityException::class)
    override fun scheduleTaskInterrupt(task: Task, date: LocalDate) {
        val intent = Intent(context, TaskBroadcastReceiver::class.java).apply {
            putExtra("TASK_ID", task.Id)
        }
        val now = OffsetDateTime.now()

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,

            task.nextNotificationDateTime(date).toEpochSecond(now.offset)*1000,
            PendingIntent.getBroadcast(context,
                task.Id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancelTaskInterrupt(task: Task) {
        TODO("Not yet implemented")
    }

}