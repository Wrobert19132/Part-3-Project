package com.example.p3project.domain.model;

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import java.time.LocalDate

data class TaskWithRelations(
    @Embedded val task: Task,
    @Relation(
            parentColumn = "taskId",
            entityColumn = "taskId"
    )
    val completions: List<Completion>,

    @Relation(
        parentColumn = "taskId",
        entityColumn = "categoryId",
        associateBy = Junction(TaskCategoryCrossRef::class)
    )
    val categories: List<Category>,


    ) {
    fun streakCount(from: LocalDate): Int {
        var lastPeriod = task.periodsPassed(from)-1

        var count: Int = 0

        val orderedCompletions = completions.reversed()

        for (completion: Completion in orderedCompletions) {
            println("Completion Period: ${completion.period} lastPeriod: $lastPeriod")


            if (completion.period <= lastPeriod) {// If the completion period is in the future, compared to given "From", skip
                if (lastPeriod == completion.period) {
                    count += 1
                } else {
                    return count
                }
                lastPeriod -= 1;
            } else if (completion.period == lastPeriod + 1) {
                if (task.isTaskDay(from)) {
                    count += 1
                }
            }
        }

        return count
    }

    fun completedToday(from: LocalDate): Boolean {
        var lastPeriod = task.periodsPassed(from)

        if (completions.isNotEmpty()) {
            return (lastPeriod == completions.reversed()[0].period)
        }
        return false
    }

}
