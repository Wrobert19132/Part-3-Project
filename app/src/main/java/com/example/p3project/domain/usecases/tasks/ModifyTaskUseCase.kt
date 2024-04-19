package com.example.p3project.domain.usecases.tasks

import com.example.p3project.domain.model.Task
import com.example.p3project.domain.repository.TaskRepository
import com.example.p3project.domain.util.taskErrorChecking
import java.time.LocalTime

class ModifyTaskUseCase (private val taskRepository: TaskRepository)
{
    suspend operator fun invoke(task: Task,
                                newName: String? = null, newDescription: String? = null,
                                newTargetTime: LocalTime? = null,
                                newNotificationOffset: Int? = null
                                ) {
        taskErrorChecking(newName, newDescription, newNotificationOffset)

        task.name = newName ?: task.name
        task.description = newDescription ?: task.description
        task.notificationOffset = newNotificationOffset ?: task.notificationOffset
        task.targetTime = newTargetTime ?: task.targetTime

        taskRepository.updateTask(task)

    }
}
