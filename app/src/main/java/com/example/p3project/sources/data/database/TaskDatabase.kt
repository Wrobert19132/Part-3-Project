package com.example.p3project.sources.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [Task::class, TaskCompletion::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun tasksDao(): TasksDao
    companion object {
        const val DATABASE_NAME = "tasksDB.db"
    }
}