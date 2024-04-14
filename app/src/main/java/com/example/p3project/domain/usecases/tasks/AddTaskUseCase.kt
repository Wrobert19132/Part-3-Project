package com.example.p3project.domain.usecases.tasks;

import com.example.p3project.domain.model.Task
import com.example.p3project.domain.repository.TaskRepository;
import com.example.p3project.domain.util.InvalidTaskException

class AddTaskUseCase (private val taskRepository: TaskRepository)
{
    suspend operator fun invoke(task: Task) {
        task.checkValid()

        if (task.taskId != 0) {
            throw InvalidTaskException("Task already has an ID, and shouldn't be re-added.")
        }



        taskRepository.addTask(task)

    }
}
