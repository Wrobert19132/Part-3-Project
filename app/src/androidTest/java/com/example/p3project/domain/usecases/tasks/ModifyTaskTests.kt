package com.example.p3project.domain.usecases.tasks

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.p3project.DBTest
import com.example.p3project.data.database.TaskDatabase
import com.example.p3project.data.repository.TaskRepositoryImpl
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.domain.repository.TaskRepository
import com.example.p3project.domain.usecases.tasks.ModifyTaskUseCase
import com.example.p3project.domain.util.InvalidTaskException
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class ModifyTaskTests: DBTest() {
    lateinit var task: Task

    @Before()
    fun setupTask() = runTest {
        val newTask = Task("Test", "Test",
            LocalTime.of(1, 30), LocalDate.of(2023, 1, 1),0, 1
        )
        repo.addTask(newTask)
        task = repo.getTaskInfo(newTask.taskId)!!.task
    }


    @Test()
    fun modifyTaskTest_modifyName() = runTest {
        val modifyTaskUseCase = ModifyTaskUseCase(repo)

        val newName = "Test2"

        modifyTaskUseCase(task, newName = newName)

        val freshTask = repo.getTaskInfo(task.taskId)

        assertEquals(newName, freshTask!!.task.name)
    }

    @Test()
    fun modifyTaskTest_modifyDescription() = runTest {
        val modifyTaskUseCase = ModifyTaskUseCase(repo)

        val newDescription = "New description"

        modifyTaskUseCase(task, newDescription = newDescription)

        val freshTask = repo.getTaskInfo(task.taskId)

        assertEquals(newDescription, freshTask!!.task.description)
    }

    @Test()
    fun modifyTaskTest_modifyTime() = runTest {
        val modifyTaskUseCase = ModifyTaskUseCase(repo)

        val newTime = LocalTime.of(5, 30)

        modifyTaskUseCase(task, newTargetTime = newTime)

        val freshTask = repo.getTaskInfo(task.taskId)

        assertEquals(newTime, freshTask!!.task.targetTime)
    }

    @Test()
    fun modifyTaskTest_modifyOffset() = runTest {
        val modifyTaskUseCase = ModifyTaskUseCase(repo)

        val newOffset = 65

        modifyTaskUseCase(task, newNotificationOffset = newOffset)

        val freshTask = repo.getTaskInfo(task.taskId)

        assertEquals(newOffset, freshTask!!.task.notificationOffset)
    }


    @Test(expected = InvalidTaskException::class)
    fun modifyTaskTest_EmptyName() = runTest {
        val modifyTaskUseCase = ModifyTaskUseCase(repo)
        val newName = ""

        modifyTaskUseCase(task, newName = newName)

        val freshTask = repo.getTaskInfo(task.taskId)

        assertEquals(newName, freshTask!!.task.name)

    }

    @Test(expected = InvalidTaskException::class)
    fun modifyTaskTest_LongName() = runTest {
        val modifyTaskUseCase = ModifyTaskUseCase(repo)
        val newName = "a".repeat(Task.maxNameLength+1)

        modifyTaskUseCase(task, newName = newName)

        val freshTask = repo.getTaskInfo(task.taskId)

        assertEquals(newName, freshTask!!.task.name)
    }

    @Test(expected = InvalidTaskException::class)
    fun modifyTaskTest_LongDescription() = runTest {
        val modifyTaskUseCase = ModifyTaskUseCase(repo)

        val newDescription = "a".repeat(Task.maxDescriptionLength+1)

        modifyTaskUseCase(task, newDescription = newDescription)

    }

    @Test(expected = InvalidTaskException::class)
    fun modifyTaskTest_LargeOffset() = runTest {
        val modifyTaskUseCase = ModifyTaskUseCase(repo)

        val newOffset = Task.maxNotificationOffset + 1

        modifyTaskUseCase(task, newNotificationOffset = newOffset)

    }

    @Test(expected = InvalidTaskException::class)
    fun modifyTaskTest_NegativeOffset() = runTest {
        val modifyTaskUseCase = ModifyTaskUseCase(repo)

        val newOffset = -1

        modifyTaskUseCase(task, newNotificationOffset = newOffset)
    }


}