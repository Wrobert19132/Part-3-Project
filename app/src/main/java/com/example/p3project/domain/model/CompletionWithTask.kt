package com.example.p3project.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import java.time.LocalDateTime


data class CompletionWithTask (
    @Embedded val task: Task,

    @Relation(
        parentColumn = "taskId",
        entityColumn = "taskId"
    )
    val completion: Completion,


) {


}