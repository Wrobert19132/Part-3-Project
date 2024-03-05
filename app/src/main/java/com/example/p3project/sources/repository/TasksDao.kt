package com.example.p3project.sources.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TasksDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addTask(task: Task)

    @Query("SELECT * FROM task")
    fun getAllTasks()
}