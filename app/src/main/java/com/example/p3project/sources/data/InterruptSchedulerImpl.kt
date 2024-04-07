package com.example.p3project.sources.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.p3project.sources.data.database.Task
import java.time.LocalDate
import java.time.OffsetDateTime

class InterruptSchedulerImpl(private val context: Context) : InterruptScheduler {
    private var alarmManager = context.getSystemService(AlarmManager::class.java)

    @Throws(SecurityException::class)
    override fun scheduleTaskInterrupt(task: Task, date: LocalDate) {
        val intent = Intent(context, TaskInterruptReceiver::class.java).apply {
            putExtra("TASK_ID", task.taskId)
        }
        val now = OffsetDateTime.now()

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,

            task.nextTaskDateTime(date).toEpochSecond(now.offset)*1000,
            PendingIntent.getBroadcast(context,
                task.taskId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancelTaskInterrupt(task: Task) {
        TODO("Not yet implemented")
    }

}