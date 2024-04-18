package com.example.p3project.domain.usecases.tasks

import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.domain.repository.TaskRepository
import com.example.p3project.domain.util.TaskViewMode

class AllTaskInfoUseCase (private val taskRepository: TaskRepository)
{
    suspend operator fun invoke(viewMode: TaskViewMode = TaskViewMode.TodayView) : List<TaskWithRelations> {
        return taskRepository.allTaskInfo()
    }
}