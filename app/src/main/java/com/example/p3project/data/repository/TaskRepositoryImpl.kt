package com.example.p3project.data.repository

import com.example.p3project.domain.model.Task
import com.example.p3project.data.database.TasksDao
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

    override suspend fun addCompletion(completion: Completion) {
        tasksDao.addCompletion(completion)
    }

}