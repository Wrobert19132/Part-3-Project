package com.example.p3project.domain.usecases.tasks

import com.example.p3project.domain.model.Task
import com.example.p3project.domain.repository.TaskRepository
import com.example.p3project.domain.util.InvalidTaskException

class ModifyTaskUseCase (private val taskRepository: TaskRepository)
{
    suspend operator fun invoke(task: Task) {
        if (task.taskId == 0) {
            throw InvalidTaskException("Task does not exist")
        }

        taskRepository.updateTask(task)

    }
}
