package com.example.p3project

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.p3project.data.database.TaskDatabase
import com.example.p3project.data.repository.TaskRepositoryImpl
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.domain.repository.TaskRepository
import com.example.p3project.domain.usecases.completions.CompleteTaskUseCase
import com.example.p3project.domain.usecases.tasks.AddTaskUseCase
import com.example.p3project.domain.usecases.tasks.GetTaskInfoUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class StreakTests {
    private lateinit var db: TaskDatabase
    private lateinit var repo: TaskRepository
    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()

        db = Room.inMemoryDatabaseBuilder(
            context, TaskDatabase::class.java).build()
        repo = TaskRepositoryImpl(db.tasksDao())


    }

    private suspend fun setup_DummyTask(now: LocalDate, streakLen: Int, periodLen: Int): TaskWithRelations {
        val addTaskUseCase = AddTaskUseCase(repo)
        val completeTaskUseCase = CompleteTaskUseCase(repo)
        val getTaskByIdUseCase = GetTaskInfoUseCase(repo)


        val task = Task("Test Task", "",
            LocalTime.of(12, 30),
            now.minusDays((periodLen * streakLen).toLong()),
            0,
            periodLen
        )
        addTaskUseCase(task)

        for (i: Int in 0..< streakLen) {
            completeTaskUseCase(task, i, LocalTime.of(0, 30))
        }

        return getTaskByIdUseCase(task.taskId)!!

    }

    @Test
    fun streakCount_onStreakDay() = runTest {
        val now = LocalDate.now()
        val taskInfo = setup_DummyTask(now, 5, 7)

        assertEquals(5, taskInfo.streakCount(now))
    }


    @Test
    fun streakCount_duringWeek() = runTest {
        val now = LocalDate.now()
        val periodLen = 7
        val taskInfo = setup_DummyTask(now, 5, periodLen)

        assertEquals(5,
            taskInfo.streakCount(now.minusDays(periodLen.floorDiv(2).toLong())))
    }

    @Test
    fun streakCount_onCompleteDay() = runTest {
        val now = LocalDate.now()
        val periodLen = 7

        val taskInfo = setup_DummyTask(now, 5, periodLen = periodLen)

        assertEquals(5, taskInfo.streakCount(now.minusDays(periodLen.toLong())))
    }

    @Test
    fun streakCount_inPast_BeforeCompleteDay() = runTest {
        val now = LocalDate.now()
        val periodLen = 7

        val taskInfo = setup_DummyTask(now, 5, periodLen = periodLen)

        assertEquals(4, taskInfo.streakCount(now.minusDays(periodLen.toLong()+1)))
    }

    @Test
    fun streakCount_missedStreak() = runTest {
        val now = LocalDate.now()
        val taskInfo = setup_DummyTask(now, 5, 7)

        assertEquals(0, taskInfo.streakCount(now.plusDays(1)))
    }
}