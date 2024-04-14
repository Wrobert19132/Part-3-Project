package com.example.p3project.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.p3project.domain.util.InvalidTaskException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import kotlin.math.ceil

@Entity
data class Task (
    var name: String,
    var description: String,

    var targetTime: LocalTime,
    var startDate: LocalDate,

    var notificationOffset: Int,

    var dayInterval: Int,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0



    fun isTaskDay(date: LocalDate): Boolean {
        val diff: Int = (date.toEpochDay() - startDate.toEpochDay()).toInt()
        return diff % dayInterval == 0
    }

    fun daysUntilNextTaskDay(date: LocalDate): Int {
        return (nextTaskDay(date).toEpochDay() - date.toEpochDay()).toInt()
    }

    fun periodsPassed(date: LocalDate): Int {
        val diff: Double = (date.toEpochDay() - startDate.toEpochDay()).toDouble()
        var periodsPassed: Double = ceil((diff / dayInterval).toDouble())
        return periodsPassed.toInt()
    }

    fun nextTaskDay(date: LocalDate): LocalDate {
        return if (isTaskDay(date)) {
            date;
        } else {
            LocalDate.ofEpochDay(
                startDate.toEpochDay() + (periodsPassed(date) * dayInterval)
            )
        }
    }

    fun nextTaskDateTime(date: LocalDate): LocalDateTime {
        return LocalDateTime.of(nextTaskDay(date), targetTime)
    }

    fun nextNotificationDateTime(date: LocalDate): LocalDateTime {
        return LocalDateTime.of(nextTaskDay(date),
            targetTime.plusMinutes(-notificationOffset.toLong())
        )
    }



    fun minutesUntilTask(dateTime: LocalDateTime): Int {
        return (daysUntilNextTaskDay(dateTime.toLocalDate()) * 1440) +
                dateTime.toLocalTime().until(targetTime, ChronoUnit.MINUTES).toInt()
    }

    fun secondsUntilTask(dateTime: LocalDateTime): Int {
        return minutesUntilTask(dateTime) * 60
    }

    fun checkValid() {
        if (name == "") {
            throw InvalidTaskException("Task cannot be empty")
        } else if (name.length > maxNameLength) {
            throw InvalidTaskException("Task name too long")
        } else if (description.length > maxDescriptionLength) {
            throw InvalidTaskException("Task description too long")
        } else if (dayInterval > maxDayInterval) {
            throw InvalidTaskException("Task day interval too great")
        } else if (notificationOffset > maxNotificationOffset) {
            throw InvalidTaskException("Task notification offset too great")

        }


    }


    companion object Limits {
        var maxNameLength: Int = 32;
        var maxDescriptionLength: Int = 128;
        var maxDayInterval: Int = 365;
        var maxNotificationOffset = 1440;

    }
}