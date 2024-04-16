package com.example.p3project.usecases.tasks

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.p3project.DBTest
import com.example.p3project.data.database.TaskDatabase
import com.example.p3project.data.repository.TaskRepositoryImpl
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.repository.TaskRepository
import com.example.p3project.domain.usecases.tasks.AddTaskUseCase
import com.example.p3project.domain.util.InvalidTaskException
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotSame
import kotlinx.coroutines.test.runTest
import org.junit.Before
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
        val taskName = "a".repeat(Task.maxNameLength+1)
        val taskDescription = "A Test Task"
        val targetTime = LocalTime.of(2, 30)
        val startDate = LocalDate.of(2024, 3, 12)
        val notificationOffset = 0
        val period = 7

        val task = addTaskUseCase.invoke(taskName, taskDescription, targetTime,
            startDate, notificationOffset, period)
    }





}