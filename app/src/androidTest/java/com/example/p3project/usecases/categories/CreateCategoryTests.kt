package com.example.p3project.usecases.categories

import androidx.compose.ui.graphics.Color
import com.example.p3project.DBTest
import com.example.p3project.domain.model.Category
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.usecases.categories.CreateCategoryUseCase
import com.example.p3project.domain.util.InvalidCategoryException
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

class CreateCategoryTests: DBTest() {
    private lateinit var task: Task

    @Before
    fun setupTask() = runTest {
        task = Task("Text",
            "A Test Task",
            LocalTime.of(2, 30),
            LocalDate.of(2024, 3, 12),
            0,
            7
        )

        repo.addTask(task)
    }


    @Test()
    fun createCategory_generalCreate() = runTest {
        val createCategoryUseCase = CreateCategoryUseCase(repo)

        val categoryName = "Test"
        val categoryColor = Color.Blue

        createCategoryUseCase(categoryName, categoryColor)

        val category = repo.getAllCategories()[0]

        assertEquals(category.categoryName, categoryName)
        assertEquals(category.getColor(), categoryColor)
    }

    @Test(expected = InvalidCategoryException::class)
    fun createCategory_EmptyName() = runTest {
        val createCategoryUseCase = CreateCategoryUseCase(repo)

        val categoryName = ""
        val categoryColor = Color.Blue

        createCategoryUseCase(categoryName, categoryColor)
    }

    @Test(expected = InvalidCategoryException::class)
    fun createCategory_LongName() = runTest {
        val createCategoryUseCase = CreateCategoryUseCase(repo)

        val categoryName = "a".repeat(Category.maxNameLength+1)
        val categoryColor = Color.Blue

        createCategoryUseCase(categoryName, categoryColor)
    }



}