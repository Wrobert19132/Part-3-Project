package com.example.p3project.sources.repository

import androidx.room.Database


@Database(
    entities = [Task::class],
    version = 1
)
abstract class TaskDatabase {
}