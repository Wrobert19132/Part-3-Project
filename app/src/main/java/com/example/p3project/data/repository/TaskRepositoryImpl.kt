package com.example.p3project.data.repository

import com.example.p3project.domain.model.Task
import com.example.p3project.data.database.TasksDao
import com.example.p3project.domain.model.Category
import com.example.p3project.domain.model.Completion
import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.domain.repository.TaskRepository

class TaskRepositoryImpl (
    private val tasksDao: TasksDao
): TaskRepository {
    override suspend fun allTaskInfo(): List<TaskWithRelations> {
        return tasksDao.getAllTaskInfo()
    }

    override suspend fun getTaskInfo(id: Int): TaskWithRelations? {
        return tasksDao.getTaskInfo(id)
    }

    override suspend fun addTask(task: Task) {
        val id: Long = tasksDao.addTask(task)
        task.taskId = id.toInt()
    }

    override suspend fun deleteTask(task: Task) {
        tasksDao.deleteTask(task)
    }


        override suspend fun updateTask(task: Task) {
        tasksDao.updateTask(task)
    }


    override suspend fun addCompletion(completion: Completion) {
        tasksDao.addCompletion(completion)
    }

    override suspend fun deleteCompletion(completion: Completion) {
        tasksDao.deleteCompletion(completion)
    }

    override suspend fun createCategory(category: Category) {
        val id: Long = tasksDao.createCategory(category)
        category.categoryId = id.toInt()
    }

    override suspend fun deleteCategory(category: Category) {
        tasksDao.unassignAllCategory(category.categoryId)
        tasksDao.deleteCategory(category)
    }

    override suspend fun getAllCategories(): List<Category> {
        return tasksDao.getAllCategories()
    }

    override suspend fun assignCategory(taskId: Int, categoryId: Int) {
        tasksDao.assignCategory(taskId, categoryId)
    }

    override suspend fun unassignCategory(taskId: Int, categoryId: Int) {
        tasksDao.unassignCategory(taskId, categoryId)

    }

}