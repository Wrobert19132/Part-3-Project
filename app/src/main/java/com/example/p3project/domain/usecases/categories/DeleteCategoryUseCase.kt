package com.example.p3project.domain.usecases.categories

import com.example.p3project.domain.model.Category
import com.example.p3project.domain.repository.TaskRepository

class DeleteCategoryUseCase(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(category: Category) {
        taskRepository.deleteCategory(category)
    }
}