package com.example.p3project.domain.usecases.charts

import android.app.TaskInfo
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.p3project.data.database.TaskDatabase
import com.example.p3project.data.repository.TaskRepositoryImpl
import com.example.p3project.domain.model.Completion
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.model.TaskWithRelations
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
class BuildCompletionDataTests {
    private lateinit var db: TaskDatabase
    private lateinit var repo: TaskRepository
    private lateinit var taskInfo: TaskWithRelations

    @Before
    fun createDb() = runTest {
        val context: Context = ApplicationProvider.getApplicationContext()

        db = Room.inMemoryDatabaseBuilder(
            context, TaskDatabase::class.java).build()
        repo = TaskRepositoryImpl(db.tasksDao())

        val task = Task("Text",
            "A Test Task",
            LocalTime.of(2, 30),
            LocalDate.of(2024, 3, 12),
            10,
            7
        )

        repo.addTask(task)
        taskInfo = repo.getTaskInfo(task.taskId)!!
    }


    @Test()
    fun buildCategoryUsage_noData() = runTest {
        val buildCompletionDataUseCase = BuildCompletionDataUseCase()

        val data = buildCompletionDataUseCase.invoke(taskInfo.task, taskInfo.completions)
        assertEquals(3, data.slices.size)
        assertEquals(0f, data.slices[0].value)
        assertEquals(0f, data.slices[1].value)
        assertEquals(0f, data.slices[2].value)
    }


    @Test()
    fun buildCategoryUsage_basicData() = runTest {
        val buildCompletionDataUseCase = BuildCompletionDataUseCase()
        val now = LocalDateTime.of(2023, 12, 3, 10, 30)
        repo.addCompletion(
            Completion(taskInfo.task.taskId, 0, taskInfo.task.dateTimeForPeriod(0).minusMinutes(30))
        )
        repo.addCompletion(Completion(taskInfo.task.taskId, 1, taskInfo.task.dateTimeForPeriod(1).minusMinutes(30)))
        repo.addCompletion(Completion(taskInfo.task.taskId, 2, taskInfo.task.dateTimeForPeriod(2)))

        taskInfo = repo.getTaskInfo(taskInfo.task.taskId)!!

        val data = buildCompletionDataUseCase.invoke(taskInfo.task, taskInfo.completions)
        assertEquals(3, data.slices.size)
        assertEquals(1f, data.slices[0].value)
        assertEquals(0f, data.slices[1].value)
        assertEquals(2f, data.slices[2].value)
    }


}