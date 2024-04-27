package com.example.p3project.domain.usecases.tasks

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.p3project.DBTest
import com.example.p3project.domain.model.Completion
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.usecases.tasks.GetTasksUseCase
import com.example.p3project.domain.util.TaskViewMode
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class AllTaskInfoTests: DBTest() {

    @Test()
    fun allTaskInfo_AllViewNormal() = runTest {
        val getTasksUseCase = GetTasksUseCase(repo)
        val now = LocalDateTime.of(2023, 3,12, 2, 30)

        val task = Task("Test", "A test task",
            LocalTime.of(2, 30),
            LocalDate.of(2024, 3, 12),
            0,7)
        repo.addTask(task)

        val taskInfos = getTasksUseCase(viewMode = TaskViewMode.AllView, now = now)

        assertEquals("correct number of tasks in result", 1, taskInfos.size)
    }

    @Test()
    fun allTaskInfo_AllViewDisabled() = runTest {
        val getTasksUseCase = GetTasksUseCase(repo)
        val now = LocalDateTime.of(2023, 3,12, 2, 30)
        val task = Task("Test", "A test task",
            LocalTime.of(2, 30),
            LocalDate.of(2024, 3, 12),
            0,7, enabled = false)
        repo.addTask(task)
        var taskInfos = getTasksUseCase(TaskViewMode.AllView, now = now)

        assertEquals("correct number of tasks in result", 0, taskInfos.size)

        taskInfos = getTasksUseCase(TaskViewMode.AllView, now = now, onlyEnabled = false)

        assertEquals("correct number of tasks in result", 1, taskInfos.size)

    }

    @Test()
    fun allTaskInfo_dayCorrect() = runTest {
        val getTasksUseCase = GetTasksUseCase(repo)
        val now = LocalDateTime.of(2024, 3,12, 2, 30)

        val task = Task("Test", "A test task",
            LocalTime.of(2, 30),
            LocalDate.of(2024, 3, 12),
            0,7
        )

        repo.addTask(task)

        var taskInfos = getTasksUseCase(viewMode = TaskViewMode.TodayView, now=now)

        assertEquals("correct number of tasks in result", 1, taskInfos.size)

        taskInfos = getTasksUseCase(viewMode = TaskViewMode.TodayView, now=now.plusDays(2))

        assertEquals("correct number of tasks in result", 0, taskInfos.size)
    }

    @Test()
    fun allTaskInfo_completeCorrect() = runTest {
        val getTasksUseCase = GetTasksUseCase(repo)
        val now = LocalDateTime.of(2024, 3,12, 2, 30)

        val task = Task("Test", "A test task",
            LocalTime.of(2, 30),
            LocalDate.of(2024, 3, 12),
            0,7
        )

        repo.addTask(task)

        var taskInfos = getTasksUseCase(viewMode = TaskViewMode.IncompleteView, now=now)

        assertEquals("correct number of tasks in result", 1, taskInfos.size)

        taskInfos = getTasksUseCase(viewMode = TaskViewMode.IncompleteView, now=now.plusDays(2))

        assertEquals("correct number of tasks in result", 0, taskInfos.size)

        repo.addCompletion(Completion(task.taskId, 0, now))
        taskInfos = getTasksUseCase(viewMode = TaskViewMode.IncompleteView, now=now)

        assertEquals("correct number of tasks in result", 0, taskInfos.size)
    }

    @Test()
    fun allTaskInfo_dayCorrectMidnightBoundary() = runTest {
        val getTasksUseCase = GetTasksUseCase(repo)
        val now = LocalDateTime.of(2024, 3,11, 23, 30)

        val task = Task("Test", "A test task",
            LocalTime.of(0, 15),
            LocalDate.of(2024, 3, 12),
            0,7
        )

        repo.addTask(task)

        var taskInfos = getTasksUseCase(viewMode = TaskViewMode.TodayView, now=now)

        assertEquals("correct number of tasks in result", 1, taskInfos.size)

        taskInfos = getTasksUseCase(viewMode = TaskViewMode.TodayView, now=now.plusDays(2))

        assertEquals("correct number of tasks in result", 0, taskInfos.size)
    }


}