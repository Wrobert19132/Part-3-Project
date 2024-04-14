package com.example.p3project.domain.repository;

import com.example.p3project.domain.model.Task;
import com.example.p3project.domain.model.Completion
import com.example.p3project.domain.model.TaskWithRelations

// An interface, so different implementations of the TaskRepository can be setup with dagger
// and hilt
interface TaskRepository {
    suspend fun getTasks(): List<Task>
    suspend fun getTask(id: Int): Task?
    suspend fun addTask(task: Task)

    suspend fun addCompletion(completion: Completion)
    suspend fun allCompletions(): List<TaskWithRelations>

    suspend fun taskCompletions(task: Task): TaskWithRelations?

}
