package com.example.p3project.sources.usecases

import com.example.p3project.sources.data.database.Task
import com.example.p3project.sources.repository.TaskRepository

class GetTaskByIdUseCase (private val taskRepository: TaskRepository)
{
    suspend operator fun invoke(id: Int) : Task? {
        return taskRepository.getTask(id)
    }
}