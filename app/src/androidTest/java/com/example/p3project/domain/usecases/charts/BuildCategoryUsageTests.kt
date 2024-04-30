package com.example.p3project.domain.usecases.charts

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.p3project.data.database.TaskDatabase
import com.example.p3project.data.repository.TaskRepositoryImpl
import com.example.p3project.domain.model.Category
import com.example.p3project.domain.model.Completion
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.domain.repository.TaskRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class BuildCategoryUsageTests {
    private lateinit var db: TaskDatabase
    private lateinit var repo: TaskRepository
    private lateinit var taskInfo: TaskWithRelations
    private lateinit var categoryA: Category
    private lateinit var categoryB: Category

    @Before
    fun createDb() = runTest {
        val context: Context = ApplicationProvider.getApplicationContext()

        db = Room.inMemoryDatabaseBuilder(
            context, TaskDatabase::class.java).build()
        repo = TaskRepositoryImpl(db.tasksDao())

        val task = Task("Text",
            "A Test Task",
            LocalTime.of(2, 30),
            LocalDate.of(2024, 3, 12),
            10,
            7
        )

        repo.addTask(task)
        categoryA = Category("Test", 0)
        repo.createCategory(categoryA)
        categoryB = Category("Testb", 0)
        repo.createCategory(categoryB)

        taskInfo = repo.getTaskInfo(task.taskId)!!
    }


    @Test()
    fun buildCategoryUsage_noData() = runTest {
        val buildCategoryUsageUseCase = BuildCategoryUsageUseCase(repo)


        val data = buildCategoryUsageUseCase.invoke()
        assertEquals(0, data.slices.size)

    }


    @Test()
    fun buildCategoryUsage_basicData() = runTest {
        val buildCategoryUsageUseCase = BuildCategoryUsageUseCase(repo)
        repo.assignCategory(taskInfo.task.taskId, categoryA.categoryId)
        repo.assignCategory(taskInfo.task.taskId, categoryB.categoryId)


        val data = buildCategoryUsageUseCase.invoke()
        assertEquals(1f, data.slices[0].value)
        assertEquals(1f, data.slices[1].value)
    }

    @Test()
    fun buildCategoryUsage_lotsOfCategories() = runTest {
        val buildCategoryUsageUseCase = BuildCategoryUsageUseCase(repo)

        for (i in 0..50) {
            var category = Category("Test", 0)
            repo.createCategory(category)
            repo.assignCategory(taskInfo.task.taskId, category.categoryId)
        }

        val data = buildCategoryUsageUseCase.invoke()
        println(data.slices.size)
    }


}