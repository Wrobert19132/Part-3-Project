package com.example.p3project.sources.usecases

import com.example.p3project.sources.data.database.Task
import com.example.p3project.sources.repository.TaskRepository

class GetTasksUseCase (private val taskRepository: TaskRepository)
{
    suspend operator fun invoke() : List<Task> {
        return taskRepository.getTasks()
    }
}