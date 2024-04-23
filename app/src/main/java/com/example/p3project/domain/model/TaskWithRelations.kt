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

    fun streakFrom(from: LocalDate): Streak {
        var lastPeriod = task.periodsPassed(from)-1

        val orderedCompletions = completions.sortedBy{-it.period}
        val streakCompletions: MutableList<Completion> = mutableListOf()

        for (completion: Completion in orderedCompletions) {
            if (completion.period <= lastPeriod) {// If the completion period is in the future, compared to given "From", skip
                if (lastPeriod == completion.period) {
                    streakCompletions += completion
                } else {
                    return Streak(task, streakCompletions)
                }
                lastPeriod -= 1;
            } else if (completion.period == lastPeriod + 1) {
                if (task.isTaskDay(from)) {
                    streakCompletions += completion
                }
            }
        }

        return Streak(task, streakCompletions)
    }

    fun longestStreak(): Streak {
        var best: List<Completion> = listOf()


        val orderedCompletions = completions.sortedBy{-it.period}
        if (completions.isEmpty()) {
            return Streak(task, listOf())
        }


        var lastPeriod = orderedCompletions[0].period+1
        val current: MutableList<Completion> = mutableListOf()

        for (completion: Completion in orderedCompletions) {
            println("$completion")
            current += completion
            if (lastPeriod != completion.period+1) {
                if (current.size > best.size) {
                    best = current
                }
                current.clear()
                current += completion
            }

            lastPeriod = completion.period;
        }

        if (current.size > best.size) {
            best = current
        }

        return Streak(task, best)

    }


    fun completedToday(from: LocalDate): Boolean {
        var lastPeriod = task.periodsPassed(from)

        if (completions.isNotEmpty()) {
            return (lastPeriod == completions.sortedBy{-it.period}[0].period)
        }
        return false
    }

    fun totalCompletions(): Int {
        return completions.size
    }

}
