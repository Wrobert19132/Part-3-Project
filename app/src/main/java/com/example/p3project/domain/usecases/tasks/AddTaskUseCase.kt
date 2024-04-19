package com.example.p3project.domain.usecases.tasks;

import com.example.p3project.domain.model.Task
import com.example.p3project.domain.repository.TaskRepository;
import com.example.p3project.domain.util.taskErrorChecking
import java.time.LocalDate
import java.time.LocalTime

class AddTaskUseCase (private val taskRepository: TaskRepository)
{
    suspend operator fun invoke(name: String, description: String,
                                targetTime: LocalTime, startDate: LocalDate,
                                notificationOffset: Int, dayInterval: Int) : Task {

        taskErrorChecking(name, description, notificationOffset, dayInterval)

        val task = Task(name, description,
            targetTime, startDate,
            notificationOffset, dayInterval)

        taskRepository.addTask(task)

        return task

    }
}
