package com.example.p3project.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.p3project.domain.model.Task

@Dao
interface TasksDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addTask(task: Task): Long

    @Query("SELECT * FROM task WHERE taskId=:id")
    fun getTask(id: Int): Task?

    @Query("SELECT * FROM task")
    suspend fun getAllTasks(): List<Task>
}