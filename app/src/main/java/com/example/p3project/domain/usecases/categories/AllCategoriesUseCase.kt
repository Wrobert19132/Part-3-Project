package com.example.p3project.domain.usecases.categories

import com.example.p3project.domain.model.Category
import com.example.p3project.domain.repository.TaskRepository

class AllCategoriesUseCase(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(): List<Category> {
        return taskRepository.getAllCategories()
    }
}