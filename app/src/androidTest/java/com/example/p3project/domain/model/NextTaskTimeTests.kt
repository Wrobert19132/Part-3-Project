package com.example.p3project.domain.model

import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDate
import java.time.LocalTime

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class NextTaskTimeTests {
    companion object {
        val start_day: LocalDate = LocalDate.of(2024, 3, 12)
        val start_hour: LocalTime = LocalTime.of(9, 30)
        val interval = 7

        val task: Task = Task(
            "Test Task",
            "Test Description",
            start_hour,
            start_day,
            0,
            interval
        )

    }

    @Test
    fun nextTaskDay_generalCorrect() {
        val now: LocalDate = LocalDate.of(2024, 3, 14)
        assertEquals("Asserts ",
            LocalDate.of(2024, 3, 19),
            task.nextTaskDay(now)
        )
    }

    @Test
    fun nextTaskDay_startDayCorrect() {
        val now: LocalDate = start_day
        assertEquals("Asserts ",
            LocalDate.of(2024, 3, 12),
            task.nextTaskDay(now)
        )
    }

    @Test
    fun nextTaskDay_onTaskDayCorrect() {
        val now: LocalDate = start_day.plusDays(interval.toLong())

        assertEquals("Asserts ",
            LocalDate.of(2024, 3, 19),
            task.nextTaskDay(now)
        )
    }

    @Test
    fun nextTaskDay_afterWhileCorrect() {
        val now: LocalDate = start_day.plusDays(interval.toLong() * 50).minusDays(5)

        val futureTaskday: LocalDate = start_day.plusDays(interval.toLong() * 50)

        assertEquals("Asserts ",
            futureTaskday,
            task.nextTaskDay(now)
        )
    }

    @Test
    fun nextTaskDay_beforeStart() {
        val now: LocalDate = start_day.minusDays(50)

        assertEquals("Asserts ",
            start_day,
            task.nextTaskDay(now)
        )
    }
}