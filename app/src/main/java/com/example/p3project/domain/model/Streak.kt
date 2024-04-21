package com.example.p3project.domain.model

import java.time.LocalDate
import java.time.LocalTime

class Streak(val completions: List<Completion>) {
    fun averageCompletionTime(): LocalTime? {
        if ((completions.size) == 0) {
            return null
        }
        return LocalTime.ofSecondOfDay(
            completions.sumOf {
                it.completionTime.toSecondOfDay()
            }.toLong() / completions.size
        )
    }

    fun size(): Int {
        return completions.size
    }

}