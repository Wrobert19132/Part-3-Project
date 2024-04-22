package com.example.p3project.domain.usecases.tasks;

import com.example.p3project.domain.model.Task
import com.example.p3project.domain.repository.TaskRepository;
import com.example.p3project.domain.util.taskErrorChecking
import java.time.LocalDate
import java.time.LocalTime

class SetTaskEnabledUseCase (private val taskRepository: TaskRepository)
{
    suspend operator fun invoke(task: Task, enabled: Boolean) : Task {
        task.enabled = enabled
        taskRepository.updateTask(task)

        return task

    }
}
