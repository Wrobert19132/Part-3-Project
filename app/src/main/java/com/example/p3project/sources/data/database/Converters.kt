package com.example.p3project.sources.data.database

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime

class Converters {
    @TypeConverter
    fun dateFromEpoch(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun dateToEpoch(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }

    @TypeConverter
    fun timeFromDaySec(value: Int?): LocalTime? {
        return value?.let { LocalTime.ofSecondOfDay(it.toLong()) }
    }

    @TypeConverter
    fun timeFromDaySec(date: LocalTime?): Int? {
        return date?.toSecondOfDay()
    }


}