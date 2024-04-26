package com.example.p3project.data.database

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime

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

    @TypeConverter
    fun dateTimeToEpoch(date: LocalDateTime?): Long? {
        return date?.toEpochSecond(OffsetDateTime.now().offset)
    }

    @TypeConverter
    fun dateTimeFromEpoch(epoch: Long?): LocalDateTime? {
        return epoch?.let{
            LocalDateTime.ofEpochSecond(it, 0,
            OffsetDateTime.now().offset)
        }
    }

}

