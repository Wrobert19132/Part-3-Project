package com.example.p3project.usecases.tasks

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.p3project.DBTest
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.usecases.tasks.ModifyTaskUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class ModifyTaskTests: DBTest() {
    @Test()
    fun modifyTaskTest_modifyName() = runTest {
        val modifyTaskUseCase = ModifyTaskUseCase(repo)
        val newName = "Test2"

        val task = Task("Test", "A test task",
            LocalTime.of(2, 30),
            LocalDate.of(2024, 3, 12),
            0,7
        )

        repo.addTask(task)
        modifyTaskUseCase(task, newName = newName)

        val freshTask = repo.getTaskInfo(task.taskId)

        assertEquals(newName, task.name)
        assertEquals(newName, freshTask!!.task.name)
    }
}