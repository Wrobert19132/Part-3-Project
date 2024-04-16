package com.example.p3project.usecases.tasks

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.p3project.DBTest
import com.example.p3project.data.database.TaskDatabase
import com.example.p3project.data.repository.TaskRepositoryImpl
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.repository.TaskRepository
import com.example.p3project.domain.usecases.tasks.AllTaskInfoUseCase
import com.example.p3project.domain.usecases.tasks.GetTaskInfoUseCase
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class AllTaskInfoTests: DBTest() {

    @Test()
    fun allTaskInfo_generalCorrect() = runTest {
        val allTaskInfoUseCase = AllTaskInfoUseCase(repo)


        val task = Task("Test", "A test task",
            LocalTime.of(2, 30),
            LocalDate.of(2024, 3, 12),
            0,7)
        repo.addTask(task)

        val taskInfos = allTaskInfoUseCase()

        assertEquals("correct number of tasks in result", 1, taskInfos.size)
    }

    @Test()
    fun allTaskInfo_empty() = runTest {
        val allTaskInfoUseCase = AllTaskInfoUseCase(repo)


        val task = Task("Test", "A test task",
            LocalTime.of(2, 30),
            LocalDate.of(2024, 3, 12),
            0,7)

        val taskInfos = allTaskInfoUseCase()

        assertEquals("correct number of tasks in result", 0, taskInfos.size)
    }


}