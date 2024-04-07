package com.example.p3project.sources.data.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDate
import java.time.LocalTime

@Entity
data class TaskCompletion (
    val taskId: Int,
    val completionTime: LocalTime,
    val period: Int
) {
    @PrimaryKey(autoGenerate = true)
    var completionId: Int = 0
}