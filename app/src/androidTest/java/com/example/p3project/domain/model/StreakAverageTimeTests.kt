package com.example.p3project.domain.model

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.p3project.data.database.TaskDatabase
import com.example.p3project.data.repository.TaskRepositoryImpl
import com.example.p3project.domain.repository.TaskRepository
import com.example.p3project.domain.usecases.completions.CompleteTaskUseCase
import com.example.p3project.domain.usecases.tasks.GetTaskUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class StreakAverageTimeTests {
    private lateinit var db: TaskDatabase
    private lateinit var repo: TaskRepository
    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()

        db = Room.inMemoryDatabaseBuilder(
            context, TaskDatabase::class.java).build()
        repo = TaskRepositoryImpl(db.tasksDao())


    }

    @Test
    fun averageTime_future() = runTest {
        val completeTaskUseCase = CompleteTaskUseCase(repo)
        val getTaskByIdUseCase = GetTaskUseCase(repo)

        val now = LocalDateTime.of(2024, 12, 5, 2, 30)

        val task = Task("Test Task", "",
            now.toLocalTime(),
            now.toLocalDate(),
            0,
            30
        )
        repo.addTask(task)

        completeTaskUseCase(task, 0, task.dateTimeForPeriod(0).plusMinutes(0))
        completeTaskUseCase(task, 1, task.dateTimeForPeriod(1).plusMinutes(10))

        val taskInfo = getTaskByIdUseCase(task.taskId)!!

        assertEquals( LocalTime.of(2, 35),
                      taskInfo.longestStreak().averageCompletionTime())
    }

    @Test
    fun averageTime_past() = runTest {
        val completeTaskUseCase = CompleteTaskUseCase(repo)
        val getTaskByIdUseCase = GetTaskUseCase(repo)

        val now = LocalDateTime.of(2024, 12, 5, 2, 30)

        val task = Task("Test Task", "",
            now.toLocalTime(),
            now.toLocalDate(),
            0,
            30
        )
        repo.addTask(task)

        completeTaskUseCase(task, 0, task.dateTimeForPeriod(0).minusMinutes(0))
        completeTaskUseCase(task, 1, task.dateTimeForPeriod(1).minusMinutes(10))

        val taskInfo = getTaskByIdUseCase(task.taskId)!!

        assertEquals( LocalTime.of(2, 25),
            taskInfo.longestStreak().averageCompletionTime()
        )
    }

    @Test
    fun averageTime_mixed() = runTest {
        val completeTaskUseCase = CompleteTaskUseCase(repo)
        val getTaskByIdUseCase = GetTaskUseCase(repo)

        val now = LocalDateTime.of(2024, 12, 5, 2, 30)

        val task = Task("Test Task", "",
            now.toLocalTime(),
            now.toLocalDate(),
            0,
            30
        )
        repo.addTask(task)

        completeTaskUseCase(task, 0, task.dateTimeForPeriod(0).plusMinutes(10))
        completeTaskUseCase(task, 1, task.dateTimeForPeriod(1).minusMinutes(20))

        val taskInfo = getTaskByIdUseCase(task.taskId)!!

        assertEquals( LocalTime.of(2, 25),
            taskInfo.longestStreak().averageCompletionTime()
        )
    }

    @Test
    fun averageTime_midnight() = runTest {
        val completeTaskUseCase = CompleteTaskUseCase(repo)
        val getTaskByIdUseCase = GetTaskUseCase(repo)

        val now = LocalDateTime.of(2024, 12, 5, 0, 0)

        val task = Task("Test Task", "",
            now.toLocalTime(),
            now.toLocalDate(),
            0,
            30
        )
        repo.addTask(task)

        completeTaskUseCase(task, 0, task.dateTimeForPeriod(0).minusMinutes(30))
        completeTaskUseCase(task, 1, task.dateTimeForPeriod(1).minusMinutes(40))

        val taskInfo = getTaskByIdUseCase(task.taskId)!!

        assertEquals( LocalTime.of(23, 25),
            taskInfo.longestStreak().averageCompletionTime()
        )
    }

}