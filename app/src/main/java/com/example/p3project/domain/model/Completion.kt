package com.example.p3project.domain.model

import androidx.room.Entity
import com.example.p3project.domain.util.CompletionTimeCategory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Entity(primaryKeys = ["taskId", "period"])
data class Completion (
    val taskId: Int,
    val period: Int,
    val completionTime: LocalTime,
) {

    fun getCategory(task: Task): CompletionTimeCategory {
        val taskTime = task.targetTime

        return if (completionTime < taskTime.minusMinutes(task.notificationOffset.toLong())) {
            CompletionTimeCategory.EarlyComplete
        } else if (completionTime < taskTime) {
            CompletionTimeCategory.OnTimeComplete
        } else {
            CompletionTimeCategory.LateComplete
        }
    }
}