package com.example.p3project.domain.usecases.tasks;

import android.app.Notification
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.repository.TaskRepository;
import com.example.p3project.domain.util.InvalidTaskException
import java.time.LocalDate
import java.time.LocalTime

class AddTaskUseCase (private val taskRepository: TaskRepository)
{
    suspend operator fun invoke(name: String, description: String,
                                targetTime: LocalTime, targetDate: LocalDate,
                                notificationOffset: Int, dayInterval: Int) : Task {
        if (name == "") {
            throw InvalidTaskException("Task cannot be empty")
        } else if (name.length > Task.maxNameLength) {
            throw InvalidTaskException("Task name too long")
        } else if (description.length > Task.maxDescriptionLength) {
            throw InvalidTaskException("Task description too long")
        } else if (dayInterval > Task.maxDayInterval) {
            throw InvalidTaskException("Task day interval too great")
        } else if (notificationOffset > Task.maxNotificationOffset) {
            throw InvalidTaskException("Task notification offset too great")

        }
        val task = Task(name, description,
            targetTime, targetDate,
            notificationOffset, dayInterval)

        taskRepository.addTask(task)

        return task

    }
}
