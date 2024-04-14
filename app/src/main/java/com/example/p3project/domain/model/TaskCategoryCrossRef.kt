package com.example.p3project.domain.model

import androidx.room.Entity

@Entity(primaryKeys = ["taskId", "categoryId"])
data class TaskCategoryCrossRef(
    val taskId: Int,
    val categoryId: Int
)