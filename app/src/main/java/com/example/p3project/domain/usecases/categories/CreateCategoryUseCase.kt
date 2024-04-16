package com.example.p3project.domain.usecases.categories

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.p3project.domain.model.Category
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.repository.TaskRepository
import com.example.p3project.domain.util.InvalidCategoryException
import com.example.p3project.domain.util.InvalidTaskException

class CreateCategoryUseCase(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(categoryName: String, categoryColor: Color) {
        if (categoryName == "") {
            throw InvalidCategoryException("Category name cannot be empty")
        } else if (categoryName.length > Category.maxNameLength) {
            throw InvalidCategoryException("Category name too long")
        }

        taskRepository.createCategory(Category(categoryName, categoryColor.toArgb()))
    }
}