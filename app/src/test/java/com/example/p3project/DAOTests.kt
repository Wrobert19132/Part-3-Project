package com.example.p3project

import com.example.p3project.domain.model.Task
import java.time.LocalDate
import java.time.LocalTime

class DAOTests {
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

}