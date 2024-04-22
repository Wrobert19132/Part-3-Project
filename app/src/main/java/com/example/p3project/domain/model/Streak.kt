package com.example.p3project.domain.model

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime

class Streak(val task: Task, val completions: List<Completion>) {
    fun averageCompletionTime(): LocalDateTime? {
        if ((completions.size) == 0) {
            return null
        }
        return LocalDateTime.ofEpochSecond(
            (completions.sumOf {
                it.completionTime(task).toEpochSecond(OffsetDateTime.now().offset).toBigInteger()
            } / completions.size.toBigInteger()).toLong(),
            0,
            OffsetDateTime.now().offset
        )
    }

    fun size(): Int {
        return completions.size
    }

}