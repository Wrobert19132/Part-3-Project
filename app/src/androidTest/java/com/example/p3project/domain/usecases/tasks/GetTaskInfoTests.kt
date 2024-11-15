package com.example.p3project.domain.usecases.tasks

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.p3project.DBTest
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.usecases.tasks.GetTaskUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class GetTaskInfoTests: DBTest() {


    @Test()
    fun getTaskInfo_generalCorrect() = runTest {
        val getTaskUseCase = GetTaskUseCase(repo)


        val task = Task("Test", "A test task",
            LocalTime.of(2, 30),
            LocalDate.of(2024, 3, 12),
            0,7)
        repo.addTask(task)

        val taskInfo = getTaskUseCase(task.taskId)

        assertEquals("Task ID is properly set", task.taskId, taskInfo!!.task.taskId)
    }

    @Test()
    fun getTaskInfo_invalidTask() = runTest {
        val getTaskUseCase = GetTaskUseCase(repo)


        val task = Task("Test", "A test task",
            LocalTime.of(2, 30),
            LocalDate.of(2024, 3, 12),
            0,7)

        val taskInfo = getTaskUseCase(task.taskId)

        assertNull("Task Info doesn't exist", taskInfo)
    }


}