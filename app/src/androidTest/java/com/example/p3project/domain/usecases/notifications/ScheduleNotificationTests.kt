package com.example.p3project.domain.usecases.notifications

import android.app.AlarmManager
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.p3project.domain.model.Task
import com.example.p3project.services.TestInterruptSchedulerService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ScheduleNotificationTests {
    private lateinit var task: Task
    private lateinit var interruptScheduler: TestInterruptSchedulerService
    @Before
    fun setupTask() = runTest {
        val context: Context = ApplicationProvider.getApplicationContext()
        context.getSystemService(AlarmManager::class.java)

        task = Task("Text",
            "A Test Task",
            LocalTime.of(2, 30),
            LocalDate.of(2024, 3, 12),
            30,
            7
        )

        interruptScheduler = TestInterruptSchedulerService()
    }

    @Test
    fun scheduleBasicTask() = runTest {
        val scheduleTaskUseCase = ScheduleTaskUseCase(interruptScheduler)

        val now = LocalDateTime.of(2024, 3, 12, 1, 25)

        scheduleTaskUseCase(task, now)

        assertEquals(
            LocalDateTime.of(2024, 3, 12, 2, 0),

            interruptScheduler.scheduledNotificationTimes[task.taskId],
        )

        assertEquals(
            LocalDateTime.of(2024, 3, 12, 2, 30),

            interruptScheduler.scheduledFollowupTimes[task.taskId],
        )
    }

    @Test
    fun scheduleAfterTaskTimeBeforeFollowup() = runTest {
        val scheduleTaskUseCase = ScheduleTaskUseCase(interruptScheduler)

        val now = LocalDateTime.of(2024, 3, 12, 2, 15)

        scheduleTaskUseCase(task, now)

        assertEquals(
            LocalDateTime.of(2024, 3, 19, 2, 0),
            interruptScheduler.scheduledNotificationTimes[task.taskId],
        )

        assertEquals(
            LocalDateTime.of(2024, 3, 12, 2, 30),
            interruptScheduler.scheduledFollowupTimes[task.taskId],
        )
    }

    @Test
    fun scheduleAfterBoth() = runTest {
        val scheduleTaskUseCase = ScheduleTaskUseCase(interruptScheduler)

        val now = LocalDateTime.of(2024, 3, 12, 2, 45)

        scheduleTaskUseCase(task, now)

        assertEquals(
            LocalDateTime.of(2024, 3, 19, 2, 0),
            interruptScheduler.scheduledNotificationTimes[task.taskId],
        )

        assertEquals(
            LocalDateTime.of(2024, 3, 19, 2, 30),
            interruptScheduler.scheduledFollowupTimes[task.taskId],
        )
    }

}