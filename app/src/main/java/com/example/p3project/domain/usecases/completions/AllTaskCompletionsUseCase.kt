package com.example.p3project.domain.usecases.completions

import com.example.p3project.domain.model.TaskWithCompletions
import com.example.p3project.domain.repository.TaskRepository

class AllTaskCompletionsUseCase (private val taskRepository: TaskRepository) {
    suspend operator fun invoke() : List<TaskWithCompletions> {
        return taskRepository.allCompletions()
    }
}