package com.example.p3project.domain.model

import androidx.room.Entity
import java.time.LocalTime

@Entity(primaryKeys = ["taskId", "period"])
data class Completion (
    val taskId: Int,
    val period: Int,
    val completionTime: LocalTime,
) {
}