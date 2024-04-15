package com.example.p3project.UseCaseTesting

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.p3project.data.database.TaskDatabase
import com.example.p3project.data.repository.TaskRepositoryImpl
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.repository.TaskRepository
import com.example.p3project.domain.usecases.tasks.AddTaskUseCase
import com.example.p3project.domain.util.InvalidTaskException
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class AddTaskTests {
    private lateinit var db: TaskDatabase
    private lateinit var repo: TaskRepository
    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()

        db = Room.inMemoryDatabaseBuilder(
            context, TaskDatabase::class.java).build()
        repo = TaskRepositoryImpl(db.tasksDao())


    }

    @Test()
    fun addTaskTest_generalCorrect() = runTest {
        val addTaskUseCase = AddTaskUseCase(repo)
        val task = Task("Text",
            "A Test Task",
            LocalTime.of(2, 30),
            LocalDate.of(2024, 3, 12),
            0,
            7
        )

        addTaskUseCase.invoke(task)

        assertEquals("Task ID is properly set", db.tasksDao().getTaskInfo(task.taskId)!!.task, task)
    }

    @Test(expected = InvalidTaskException::class)
    fun addTaskTest_EmptyName() = runTest {
        val addTaskUseCase = AddTaskUseCase(repo)
        val task = Task("",
            "A Test Task",
            LocalTime.of(2, 30),
            LocalDate.of(2024, 3, 12),
            0,
            7
        )

        addTaskUseCase.invoke(task)

        assertEquals("Task is not added", db.tasksDao().getTaskInfo(task.taskId), null)
    }

    @Test(expected = InvalidTaskException::class)
    fun addTaskTest_LongName() = runTest {
        val addTaskUseCase = AddTaskUseCase(repo)
        val task = Task("a".repeat(Task.maxNameLength+1),
            "A Test Task",
            LocalTime.of(2, 30),
            LocalDate.of(2024, 3, 12),
            0,
            7
        )

        addTaskUseCase.invoke(task)

        assertEquals("Task is not added", db.tasksDao().getTaskInfo(task.taskId), null)
    }





}