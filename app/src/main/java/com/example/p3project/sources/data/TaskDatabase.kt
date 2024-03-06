package com.example.p3project.sources.data

import androidx.room.Database
import com.example.p3project.sources.data.Task


@Database(
    entities = [Task::class],
    version = 1
)
abstract class TaskDatabase {
}