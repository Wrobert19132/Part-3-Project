package com.example.p3project.domain.usecases.categories;

import com.example.p3project.domain.model.Category
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.repository.TaskRepository;

public class AssignCategoryUseCase(private val taskRepository:TaskRepository) {
    suspend operator fun invoke(task: Task, category: Category) {
        taskRepository.assignCategory(task.taskId, category.categoryId)
    }
}
