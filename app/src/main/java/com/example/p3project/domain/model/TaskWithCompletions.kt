package com.example.p3project.domain.model;

import androidx.room.Embedded
import androidx.room.Relation
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.model.TaskCompletion

data class TaskWithCompletions(
        @Embedded val task: Task,
        @Relation(
                parentColumn = "taskId",
                entityColumn = "taskId"
        )
        val completions: List<TaskCompletion>,
)
