package com.example.p3project.domain.repository;

import com.example.p3project.domain.model.Task;
import com.example.p3project.domain.model.TaskCompletion
import com.example.p3project.domain.model.TaskWithCompletions
import java.time.LocalDate

// An interface, so different implementations of the TaskRepository can be setup with dagger
// and hilt
interface TaskRepository {
    suspend fun getTasks(): List<Task>
    suspend fun getTask(id: Int): Task?
    suspend fun addTask(task: Task)

    suspend fun addCompletion(taskCompletion: TaskCompletion)
    suspend fun getCompletions(taskId: Int, maxPeriod: Int): TaskWithCompletions?

}
