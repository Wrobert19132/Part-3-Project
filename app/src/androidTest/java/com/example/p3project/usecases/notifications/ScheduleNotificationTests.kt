package com.example.p3project.usecases.notifications

import android.app.AlarmManager
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.p3project.presentation.services.InterruptSchedulerService
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.service.InterruptScheduler
import com.example.p3project.domain.usecases.notifications.ScheduleTaskUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

class ScheduleNotificationTests {
    private lateinit var task: Task
    private lateinit var interruptScheduler: InterruptScheduler
    private lateinit var alarmManager: AlarmManager
    @Before
    fun setupTask() = runTest {
        val context: Context = ApplicationProvider.getApplicationContext()
        context.getSystemService(AlarmManager::class.java)

        task = Task("Text",
            "A Test Task",
            LocalTime.of(2, 30),
            LocalDate.of(2024, 3, 12),
            0,
            7
        )

        interruptScheduler = InterruptSchedulerService(context)
    }

    @Test
    fun scheduleTask() = runTest {
        val scheduleTaskUseCase = ScheduleTaskUseCase(interruptScheduler)

        interruptScheduler.scheduleTaskNotificationInterrupt(task, LocalDate.now())
        interruptScheduler.cancelTaskNotificationInterrupt(task)
    }
}