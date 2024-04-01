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
        return daysUntilNextTaskDay(date) == 0
    }

    fun daysUntilNextTaskDay(date: LocalDate): Int {
        return ((date.toEpochDay() - startDate.toEpochDay())  % dayInterval).toInt()
    }

    fun nextTaskDay(date: LocalDate): LocalDate {
        return LocalDate.ofEpochDay(date.toEpochDay() + daysUntilNextTaskDay(date))
    }

    fun minutesUntilTask(dateTime: LocalDateTime): Long {
        return (daysUntilNextTaskDay(dateTime.toLocalDate()) * 1440) +
                dateTime.toLocalTime().until(time, ChronoUnit.MINUTES)
    }

    companion object Limits {
        var maxNameLength: Int = 32;
        var maxDescriptionLength: Int = 128;
        var maxDayInterval: Int = 365;

    }
}