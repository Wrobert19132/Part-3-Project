package com.example.p3project.sources.repository

import com.example.p3project.sources.data.database.Task
import com.example.p3project.sources.data.database.TasksDao

class TaskRepositoryImpl (
    private val tasksDao: TasksDao
): TaskRepository {
    override suspend fun getTasks(): List<Task> {
        return tasksDao.getAllTasks()
    }

    override suspend fun getTask(id: Int): Task? {
        return tasksDao.getTask(id)
    }

    override suspend fun addTask(task: Task) {
        val id: Long = tasksDao.addTask(task)
        task.id = id.toInt()
    }

}