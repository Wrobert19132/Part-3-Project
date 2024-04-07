package com.example.p3project.sources.data.database;

import androidx.room.Embedded
import androidx.room.Relation

data class TaskWithCompletions(
        @Embedded val task: Task,
        @Relation(
                parentColumn = "taskId",
                entityColumn = "taskId"
        )
        val completions: List<TaskCompletion>
)
