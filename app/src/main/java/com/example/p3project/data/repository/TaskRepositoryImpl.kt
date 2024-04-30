package com.example.p3project.data.repository

import com.example.p3project.domain.model.Task
import com.example.p3project.data.database.TasksDao
import com.example.p3project.domain.model.Category
import com.example.p3project.domain.model.CategoryCount
import com.example.p3project.domain.model.Completion
import com.example.p3project.domain.model.TaskCategoryCrossRef
import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.domain.repository.TaskRepository

class TaskRepositoryImpl (
    private val tasksDao: TasksDao
): TaskRepository {
    override suspend fun allTaskInfo(categoryFilters: List<Category>): List<TaskWithRelations> {
        return if (categoryFilters.isEmpty()) {
            tasksDao.getAllTaskInfo()
        } else {
            tasksDao.getFilteredTaskInfo(categoryFilters.map { it.categoryId })
        }
    }

    override suspend fun getTaskInfo(id: Int): TaskWithRelations? {
        return tasksDao.getTaskInfo(id)
    }

    override suspend fun categoryUsage(): List<CategoryCount> {
        return tasksDao.categoryUsage()
    }

    override suspend fun addTask(task: Task) {
        val id: Long = tasksDao.addTask(task)
        task.taskId = id.toInt()
    }

    override suspend fun deleteTask(task: Task) {
        tasksDao.deleteTask(task)
        tasksDao.deleteTaskCategories(task.taskId)
        tasksDao.deleteTaskCompletions(task.taskId)
    }


        override suspend fun updateTask(task: Task) {
        tasksDao.updateTask(task)
    }


    override suspend fun addCompletion(completion: Completion) {
        tasksDao.addCompletion(completion)
    }

    override suspend fun deleteCompletion(period: Int, taskId: Int) {
        tasksDao.deleteCompletion(period, taskId)
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
        tasksDao.assignCategory(TaskCategoryCrossRef(taskId, categoryId))
    }

    override suspend fun unassignCategory(taskId: Int, categoryId: Int) {
        tasksDao.unassignCategory(taskId, categoryId)

    }

}