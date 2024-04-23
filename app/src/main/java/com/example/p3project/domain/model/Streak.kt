package com.example.p3project.domain.model

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime

class Streak(val task: Task, val completions: List<Completion>) {
    fun averageCompletionTime(): LocalTime? {
        if ((completions.size) == 0) {
            return null
        }
        return LocalTime.ofSecondOfDay(
            (completions.sumOf {
                it.completionTime.toSecondOfDay()
            } / completions.size).toLong()
        )
    }

    fun size(): Int {
        return completions.size
    }

}