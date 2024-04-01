package com.example.p3project.sources.repository;

import com.example.p3project.sources.data.database.Task;

// An interface, so different implementations of the TaskRepository can be setup with dagger
// and hilt
interface TaskRepository {
    suspend fun getTasks(): List<Task>
    suspend fun addTask(task: Task)

}
