package com.example.p3project.domain.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDate
import java.time.LocalTime

@Entity(primaryKeys = ["taskId", "period"])
data class TaskCompletion (
    val taskId: Int,
    val period: Int,
    val completionTime: LocalTime,
) {
}