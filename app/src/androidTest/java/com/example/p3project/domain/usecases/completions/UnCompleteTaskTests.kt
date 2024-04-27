package com.example.p3project.domain.usecases.completions

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
class UnCompleteTaskTests {
    private lateinit var db: TaskDatabase
    private lateinit var repo: TaskRepository
    private lateinit var task: Task
    private lateinit var taskInfo: TaskWithRelations

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

        taskInfo = repo.getTaskInfo(task.taskId)!!
    }


    @Test()
    fun uncompleteTask_generalCorrect() = runTest {
        val uncompleteTaskUseCase = UncompleteTaskUseCase(repo)

        repo.addCompletion(
            Completion(task.taskId, 0,
                LocalDateTime.of(2023, 1, 1, 0, 30)
            )
        )


        assertEquals(0, repo.getTaskInfo(taskInfo.task.taskId)!!.completions[0].period)
        uncompleteTaskUseCase(task, 0)

        assertEquals(listOf<Completion>(),
            repo.getTaskInfo(taskInfo.task.taskId)!!.completions)

    }

    @Test()
    fun completeTask_noExist() = runTest {
        val uncompleteTaskUseCase = UncompleteTaskUseCase(repo)
        uncompleteTaskUseCase(taskInfo.task, 0)

        assertEquals(listOf<Completion>(), repo.getTaskInfo(task.taskId)!!.completions)
    }


}