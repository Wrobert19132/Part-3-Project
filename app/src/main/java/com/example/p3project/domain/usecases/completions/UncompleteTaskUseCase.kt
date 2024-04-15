package com.example.p3project.domain.usecases.completions

import com.example.p3project.domain.model.Task
import com.example.p3project.domain.model.Completion
import com.example.p3project.domain.repository.TaskRepository
import java.time.LocalTime

class UncompleteTaskUseCase (private val taskRepository: TaskRepository) {
    suspend operator fun invoke(completion: Completion) {
        taskRepository.deleteCompletion(completion)
    }
}