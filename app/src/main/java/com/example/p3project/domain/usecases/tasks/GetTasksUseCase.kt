package com.example.p3project.domain.usecases.tasks

import com.example.p3project.domain.model.Task
import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.domain.repository.TaskRepository

class GetTasksUseCase (private val taskRepository: TaskRepository)
{
    suspend operator fun invoke() : List<TaskWithRelations> {
        return taskRepository.getTasks()
    }
}