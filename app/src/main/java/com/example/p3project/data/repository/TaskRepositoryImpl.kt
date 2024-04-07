package com.example.p3project.data.repository

import com.example.p3project.domain.model.Task
import com.example.p3project.data.database.TasksDao
import com.example.p3project.domain.repository.TaskRepository

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
        task.taskId = id.toInt()
    }

}