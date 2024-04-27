package com.example.p3project.domain.usecases.tasks

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.p3project.DBTest
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.usecases.tasks.AddTaskUseCase
import com.example.p3project.domain.util.InvalidTaskException
import junit.framework.TestCase.assertNotSame
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class AddTaskTests: DBTest() {


    @Test()
    fun addTaskTest_generalCorrect() = runTest {
        val addTaskUseCase = AddTaskUseCase(repo)
        val taskName = "Text"
        val taskDescription = "A Test Task"
        val targetTime = LocalTime.of(2, 30)
        val startDate = LocalDate.of(2024, 3, 12)
        val notificationOffset = 0
        val period = 7

        val task = addTaskUseCase.invoke(taskName, taskDescription, targetTime,
                                         startDate, notificationOffset, period)

        assertNotSame("Task ID is properly set", task.taskId, 0)
    }

    @Test(expected = InvalidTaskException::class)
    fun addTaskTest_EmptyName() = runTest {
        val addTaskUseCase = AddTaskUseCase(repo)
        val taskName = ""
        val taskDescription = "A Test Task"
        val targetTime = LocalTime.of(2, 30)
        val startDate = LocalDate.of(2024, 3, 12)
        val notificationOffset = 0
        val period = 7

        val task = addTaskUseCase.invoke(taskName, taskDescription, targetTime,
            startDate, notificationOffset, period)

    }

    @Test(expected = InvalidTaskException::class)
    fun addTaskTest_LongName() = runTest {
        val addTaskUseCase = AddTaskUseCase(repo)
        val taskName = "a".repeat(Task.maxNameLength + 1)
        val taskDescription = "A Test Task"
        val targetTime = LocalTime.of(2, 30)
        val startDate = LocalDate.of(2024, 3, 12)
        val notificationOffset = 0
        val period = 7

        val task = addTaskUseCase.invoke(taskName, taskDescription, targetTime,
            startDate, notificationOffset, period)
    }

    @Test(expected = InvalidTaskException::class)
    fun addTaskTest_LongDescription() = runTest {
        val addTaskUseCase = AddTaskUseCase(repo)
        val taskName = "Test Task"
        val taskDescription = "a".repeat(Task.maxDescriptionLength + 1)
        val targetTime = LocalTime.of(2, 30)
        val startDate = LocalDate.of(2024, 3, 12)
        val notificationOffset = 0
        val period = 7

        val task = addTaskUseCase.invoke(taskName, taskDescription, targetTime,
            startDate, notificationOffset, period)
    }

    @Test(expected = InvalidTaskException::class)
    fun addTaskTest_LargeOffset() = runTest {
        val addTaskUseCase = AddTaskUseCase(repo)
        val taskName = "Test Task"
        val taskDescription = "a"
        val targetTime = LocalTime.of(2, 30)
        val startDate = LocalDate.of(2024, 3, 12)
        val notificationOffset = Task.maxNotificationOffset + 1
        val period = 7

        val task = addTaskUseCase.invoke(taskName, taskDescription, targetTime,
            startDate, notificationOffset, period)
    }

    @Test(expected = InvalidTaskException::class)
    fun addTaskTest_NegativeOffset() = runTest {
        val addTaskUseCase = AddTaskUseCase(repo)
        val taskName = "Test Task"
        val taskDescription = "a"
        val targetTime = LocalTime.of(2, 30)
        val startDate = LocalDate.of(2024, 3, 12)
        val notificationOffset = -30
        val period = 7

        val task = addTaskUseCase.invoke(taskName, taskDescription, targetTime,
            startDate, notificationOffset, period)
    }

    @Test(expected = InvalidTaskException::class)
    fun addTaskTest_LargePeriod() = runTest {
        val addTaskUseCase = AddTaskUseCase(repo)
        val taskName = "Test Task"
        val taskDescription = "a"
        val targetTime = LocalTime.of(2, 30)
        val startDate = LocalDate.of(2024, 3, 12)
        val notificationOffset = 5
        val period = Task.maxDayInterval + 1

        val task = addTaskUseCase.invoke(taskName, taskDescription, targetTime,
            startDate, notificationOffset, period)
    }

    @Test(expected = InvalidTaskException::class)
    fun addTaskTest_NegativePeriod() = runTest {
        val addTaskUseCase = AddTaskUseCase(repo)
        val taskName = "Test Task"
        val taskDescription = "a"
        val targetTime = LocalTime.of(2, 30)
        val startDate = LocalDate.of(2024, 3, 12)
        val notificationOffset = 5
        val period = -3

        val task = addTaskUseCase.invoke(taskName, taskDescription, targetTime,
            startDate, notificationOffset, period)
    }





}