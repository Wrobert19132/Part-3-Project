package com.example.p3project.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.model.TaskCompletion
import com.example.p3project.domain.model.TaskWithCompletions

@Dao
interface TasksDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addTask(task: Task): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(task: Task): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCompletion(completion: TaskCompletion)

    @Query("SELECT * FROM task WHERE taskId=:id")
    fun getTask(id: Int): Task?

    @Query("SELECT * FROM task")
    suspend fun getAllTasks(): List<Task>

    @Transaction
    @Query("SELECT * FROM TaskCompletion " +
            "JOIN Task ON TaskCompletion.TaskID = Task.TaskID WHERE Task.TaskID = :id " +
            "AND TaskCompletion.period <= :maxPeriod")
    suspend fun taskWithCompletions(id: Int, maxPeriod: Int): TaskWithCompletions?

}