package com.example.p3project.usecases.tasks

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.p3project.DBTest
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.usecases.tasks.DeleteTaskUseCase
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class DeleteTaskTests: DBTest() {

    @Test()
    fun deleteTaskTest_generalCorrect() = runTest {
        val deleteTaskUseCase = DeleteTaskUseCase(repo)

        val task = Task("Test", "A test task",
            LocalTime.of(2, 30),
            LocalDate.of(2024, 3, 12),
            0,7
        )
        repo.addTask(task)

        assertNotNull(repo.getTaskInfo(task.taskId))

        deleteTaskUseCase(task)

        assertNull(repo.getTaskInfo(task.taskId))

    }



}