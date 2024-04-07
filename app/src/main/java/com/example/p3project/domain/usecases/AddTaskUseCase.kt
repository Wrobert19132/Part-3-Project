package com.example.p3project.domain.usecases;

import com.example.p3project.domain.model.Task
import com.example.p3project.domain.repository.TaskRepository;

class AddTaskUseCase (private val taskRepository: TaskRepository)
{
    suspend operator fun invoke(task: Task) {
        taskRepository.addTask(task)
    }
}
