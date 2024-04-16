package com.example.p3project.domain.usecases.categories

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.p3project.domain.model.Category
import com.example.p3project.domain.repository.TaskRepository

class CreateCategoryUseCase(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(categoryName: String , categoryColor: Color) {
        taskRepository.createCategory(Category(categoryName, categoryColor.toArgb()))
    }
}