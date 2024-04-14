package com.example.p3project.domain.usecases.completions

import com.example.p3project.domain.model.Task
import com.example.p3project.domain.model.TaskWithCompletions
import com.example.p3project.domain.repository.TaskRepository

class GetTaskCompletionsUseCase (private val taskRepository: TaskRepository) {
    suspend operator fun invoke(task: Task) : TaskWithCompletions {
        return taskRepository.taskCompletions(task)!!
    }
}