package com.example.p3project.domain.usecases.completions

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.p3project.data.database.TaskDatabase
import com.example.p3project.data.repository.TaskRepositoryImpl
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.repository.TaskRepository
import com.example.p3project.domain.usecases.completions.CompleteTaskUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class CompleteTaskTests {
    private lateinit var db: TaskDatabase
    private lateinit var repo: TaskRepository
    private lateinit var task: Task

    @Before
    fun createDb() = runTest {
        val context: Context = ApplicationProvider.getApplicationContext()

        db = Room.inMemoryDatabaseBuilder(
            context, TaskDatabase::class.java).build()
        repo = TaskRepositoryImpl(db.tasksDao())

        task = Task("Text",
            "A Test Task",
            LocalTime.of(2, 30),
            LocalDate.of(2024, 3, 12),
            0,
            7
        )

        repo.addTask(task)
    }


    @Test()
    fun completeTask_generalCorrect() = runTest {
        val completeTaskUseCase = CompleteTaskUseCase(repo)

        completeTaskUseCase.invoke(task, 0, task.startDate.atTime(1, 30))

        assertEquals(0, repo.getTaskInfo(task.taskId)!!.completions.get(0).period)
    }

    @Test()
    fun completeTask_midnight() = runTest {
        val completeTaskUseCase = CompleteTaskUseCase(repo)

        val now = LocalDateTime.of(2024, 3, 11, 9, 30)

        val midnightTask = Task("Text",
            "A Test Task",
            LocalTime.of(0, 30),
            LocalDate.of(2024, 3, 12),
            0,
            7
        )

        repo.addTask(midnightTask)

        completeTaskUseCase.invoke(task, 0, now)

        assertEquals(true, repo.getTaskInfo(task.taskId)!!.completedOnDay(task.nextTaskDay(now.toLocalDate())))

    }



    @Test()
    fun completeTask_alreadyComplete() = runTest {
        val completeTaskUseCase = CompleteTaskUseCase(repo)

        completeTaskUseCase.invoke(task, 0, task.startDate.atTime(1, 30))

        completeTaskUseCase.invoke(task, 0, task.startDate.atTime(1, 50))

        assertEquals(repo.getTaskInfo(task.taskId)!!.completions.get(0).period, 0)
    }


}