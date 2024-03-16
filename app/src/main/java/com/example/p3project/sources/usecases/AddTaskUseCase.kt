package com.example.p3project.sources.usecases;

import com.example.p3project.sources.data.Task
import com.example.p3project.sources.repository.TaskRepository;

class AddTaskUseCase (private val taskRepository:TaskRepository)
{
    suspend operator fun invoke(task: Task) {
        taskRepository.addTask(task)
    }
}
