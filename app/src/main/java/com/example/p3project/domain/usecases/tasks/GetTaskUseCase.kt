package com.example.p3project.domain.usecases.tasks

import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.domain.repository.TaskRepository

class GetTaskUseCase (private val taskRepository: TaskRepository)
{
    suspend operator fun invoke(id: Int) : TaskWithRelations? {
        return taskRepository.getTaskInfo(id)
    }
}