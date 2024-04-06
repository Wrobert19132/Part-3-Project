package com.example.p3project.sources.data;

import com.example.p3project.sources.data.database.Task;
import java.time.LocalDate

interface InterruptScheduler {
    fun scheduleTaskInterrupt(task: Task, day: LocalDate)
    fun cancelTaskInterrupt(task: Task)
}
