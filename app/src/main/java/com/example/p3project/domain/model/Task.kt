package com.example.p3project.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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
    @ColumnInfo(name = "enabled", defaultValue = "1") var enabled: Boolean = true
)
{
    @PrimaryKey(autoGenerate = true)
    var taskId: Int = 0



    fun isTaskDay(date: LocalDate): Boolean {
        if (date < startDate) {
            return false
        }
        val diff: Int = (date.toEpochDay() - startDate.toEpochDay()).toInt()
        return diff % dayInterval == 0
    }

    fun isTaskDay(date: LocalDateTime): Boolean {

        val next: LocalDateTime = nextTaskDateTime(date.toLocalDate())
        return (next.toLocalDate() == date.toLocalDate()
                || next.minusMinutes(600) < date
                || nextTaskDateTime(date.minusMinutes(300).toLocalDate()).plusMinutes(300) < date )
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
        if (date < startDate) {
            return startDate
        }
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
    fun dateTimeForPeriod(periods: Int): LocalDateTime {
        return startDate.plusDays(periods.toLong()).atTime(targetTime)
    }

    fun nextNotificationDateTime(periods: Int): LocalDateTime {
        return startDate.plusDays(periods.toLong()).atTime(
            targetTime.plusMinutes(-notificationOffset.toLong())
        )
    }

    fun periodLengthMinutes(): Int {
        return dayInterval * 1440
    }

    fun minutesUntilTask(dateTime: LocalDateTime): Int {
        return (daysUntilNextTaskDay(dateTime.toLocalDate()) * 1440) +
                dateTime.toLocalTime().until(targetTime, ChronoUnit.MINUTES).toInt()
    }

    companion object Limits {
        var maxNameLength: Int = 32;
        var maxDescriptionLength: Int = 128;
        var maxDayInterval: Int = 365;
        var maxNotificationOffset = 1440;

    }
}