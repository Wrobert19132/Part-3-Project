package com.example.p3project.sources.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.p3project.sources.data.Task

@Dao
interface TasksDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addTask(task: Task)

    @Query("SELECT * FROM task")
    fun getAllTasks(): List<Task>
}