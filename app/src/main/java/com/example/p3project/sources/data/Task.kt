package com.example.p3project.sources.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date

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

    companion object Limits {
        var maxNameLength: Int = 32;
        var maxDescriptionLength: Int = 128;
        var maxDayInterval: Int = 365;

    }
}