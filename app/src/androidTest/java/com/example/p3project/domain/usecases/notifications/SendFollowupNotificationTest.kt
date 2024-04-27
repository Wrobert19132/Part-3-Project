package com.example.p3project.domain.usecases.notifications

import android.app.AlarmManager
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.p3project.domain.model.Task
import com.example.p3project.services.TestInterruptSchedulerService
import com.example.p3project.services.TestNotificationService
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

class SendFollowupNotificationTest {
    private lateinit var task: Task
    private lateinit var notificationService: TestNotificationService
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

        notificationService = TestNotificationService()
    }

    @Test()
    fun sendNotification_sendFollowup() = runTest {
        notificationService.followUpNotification(task)
        assert(notificationService.sentFollowupNotifications.size == 1)
    }
}