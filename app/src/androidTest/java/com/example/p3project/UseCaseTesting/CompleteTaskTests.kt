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
import com.example.p3project.domain.usecases.completions.CompleteTaskUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class CompleteTaskTests {
    private lateinit var db: TaskDatabase
    private lateinit var repo: TaskRepository
    private lateinit var task: Task

    @Before
    fun createDb() = runTest {
        val context: Context = ApplicationProvider.getApplicationContext()

        db = Room.inMemoryDatabaseBuilder(
            context, TaskDatabase::class.java).build()
        repo = TaskRepositoryImpl(db.tasksDao())

        val addTaskUseCase = AddTaskUseCase(repo)

        task = Task("Text",
            "A Test Task",
            LocalTime.of(2, 30),
            LocalDate.of(2024, 3, 12),
            0,
            7
        )

        addTaskUseCase.invoke(task)
    }


    @Test()
    fun completeTask_generalCorrect() = runTest {
        val completeTaskUseCase = CompleteTaskUseCase(repo)

        completeTaskUseCase.invoke(task, 0, LocalTime.of(1, 30))

    }
}