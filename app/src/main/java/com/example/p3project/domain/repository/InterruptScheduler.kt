package com.example.p3project.domain.repository;

import com.example.p3project.domain.model.Task;
import java.time.LocalDate

interface InterruptScheduler {
    fun scheduleTaskInterrupt(task: Task, day: LocalDate)
    fun cancelTaskInterrupt(task: Task)
}
