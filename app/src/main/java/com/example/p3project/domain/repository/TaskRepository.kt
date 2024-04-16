package com.example.p3project.domain.repository;

import com.example.p3project.domain.model.Category
import com.example.p3project.domain.model.Task;
import com.example.p3project.domain.model.Completion
import com.example.p3project.domain.model.TaskWithRelations

// An interface, so different implementations of the TaskRepository can be setup with dagger
// and hilt
interface TaskRepository {

    suspend fun addTask(task: Task)

    suspend fun deleteTask(task: Task)
    suspend fun updateTask(task: Task)

    suspend fun getTaskInfo(id: Int): TaskWithRelations?
    suspend fun allTaskInfo(): List<TaskWithRelations>


    suspend fun addCompletion(completion: Completion)
    suspend fun deleteCompletion(completion: Completion)

    suspend fun createCategory(category: Category)
    suspend fun deleteCategory(category: Category)
    suspend fun getAllCategories(): List<Category>

    suspend fun assignCategory(taskId: Int, categoryId: Int)
    suspend fun unassignCategory(taskId: Int, categoryId: Int)

}
