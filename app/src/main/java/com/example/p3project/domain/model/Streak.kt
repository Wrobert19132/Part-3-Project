package com.example.p3project.domain.model

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime

class Streak(val task: Task, val completions: List<Completion>) {
    fun averageCompletionTime(): LocalTime? {
        if ((completions.size) == 0) {
            return null
        }

        val offset = OffsetDateTime.now().offset

        return task.timeForPeriod(0).minusSeconds(
            (completions.sumOf {
                task.timeForPeriod(it.period).toEpochSecond(offset) - it.completionTime.toEpochSecond(offset)
            } / completions.size)
        ).toLocalTime()
    }

    fun size(): Int {
        return completions.size
    }

}