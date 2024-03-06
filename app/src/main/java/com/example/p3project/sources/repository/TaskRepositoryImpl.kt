package com.example.p3project.sources.repository

import com.example.p3project.sources.data.Task
import com.example.p3project.sources.data.TasksDao

class TaskRepositoryImpl (
    private val tasksDao: TasksDao
): TaskRepository {
    override suspend fun getTasks(task: Task): List<Task> {
        return tasksDao.getAllTasks()
    }

    override suspend fun addTask(task: Task) {
        tasksDao.addTask(task)
    }

}