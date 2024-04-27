package com.example.p3project.domain.usecases.categories

import android.database.sqlite.SQLiteConstraintException
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.p3project.DBTest
import com.example.p3project.domain.model.Category
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.usecases.categories.AssignCategoryUseCase
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

class AllCategoriesTests: DBTest()  {
    private lateinit var task: Task
    private lateinit var category: Category

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

        category = Category("Test", Color.Blue.toArgb())
        repo.createCategory(category)
    }

    @Test()
    fun allCategories_generalCorrect() = runTest {
        val allCategoriesUseCase = AllCategoriesUseCase(repo)

        TestCase.assertEquals(1, allCategoriesUseCase().size)
    }

    @Test()
    fun allCategories_noCategories() = runTest {
        val allCategoriesUseCase = AllCategoriesUseCase(repo)

        repo.deleteCategory(category)

        TestCase.assertEquals(0, allCategoriesUseCase().size)
    }
}