package com.example.p3project.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.model.TaskCategory
import com.example.p3project.domain.model.TaskCompletion


@Database(
    entities = [Task::class, TaskCompletion::class, TaskCategory::class],
    version = 3
)
@TypeConverters(Converters::class)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun tasksDao(): TasksDao
    companion object {
        const val DATABASE_NAME = "tasksDB.db"
    }
}