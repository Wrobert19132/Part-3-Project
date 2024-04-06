package com.example.p3project.sources.data.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.lang.Math.floorDiv
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit
import java.util.Date
import kotlin.math.ceil
import kotlin.time.DurationUnit

@Entity
data class Task (
    var name: String,
    var description: String,
    var time: LocalTime,
    var startDate: LocalDate,
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
        return LocalDateTime.of(nextTaskDay(date), time)
    }


    fun minutesUntilTask(dateTime: LocalDateTime): Long {
        return (daysUntilNextTaskDay(dateTime.toLocalDate()) * 1440) +
                dateTime.toLocalTime().until(time, ChronoUnit.MINUTES)
    }

    fun secondsUntilTask(dateTime: LocalDateTime): Long {
        return minutesUntilTask(dateTime) * 60
    }

    companion object Limits {
        var maxNameLength: Int = 32;
        var maxDescriptionLength: Int = 128;
        var maxDayInterval: Int = 365;

    }
}