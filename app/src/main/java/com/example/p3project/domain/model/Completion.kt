package com.example.p3project.domain.model

import androidx.room.Entity
import com.example.p3project.domain.util.CompletionTimeCategory
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(primaryKeys = ["taskId", "period"])
data class Completion (
    val taskId: Int,
    val period: Int,
    val completionSecondsBefore: Int,
) {

    fun completionTime(task: Task): LocalDateTime {
        return task.nextTaskDateTime(period)
            .minusSeconds(completionSecondsBefore.toLong())
    }

    fun getCategory(task: Task): CompletionTimeCategory {
        val taskTime = task.nextTaskDateTime(LocalDate.now())

        val completionDateTime = taskTime.minusSeconds(completionSecondsBefore.toLong())

        return if (completionDateTime < task.nextNotificationDateTime(period)) {
            CompletionTimeCategory.EarlyComplete
        } else if (completionDateTime < task.nextTaskDateTime(period)) {
            CompletionTimeCategory.OnTimeComplete
        } else {
            CompletionTimeCategory.LateComplete
        }
    }
}