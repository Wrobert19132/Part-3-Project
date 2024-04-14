package com.example.p3project.domain.usecases.tasks

import com.example.p3project.domain.model.Task
import com.example.p3project.domain.repository.TaskRepository

class GetTaskByIdUseCase (private val taskRepository: TaskRepository)
{
    suspend operator fun invoke(id: Int) : Task? {
        return taskRepository.getTask(id)
    }
}