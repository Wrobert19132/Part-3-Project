package com.example.p3project.sources.usecases

import com.example.p3project.sources.repository.TaskRepository

class GetTasksUseCase (private val taskRepository: TaskRepository)
{
    suspend operator fun invoke() {
        taskRepository.getTasks()
    }
}