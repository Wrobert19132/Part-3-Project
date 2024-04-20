package com.example.p3project.domain.repository;

import com.example.p3project.domain.model.Task;
import java.time.LocalDate

interface InterruptScheduler {
    fun scheduleTaskNotificationInterrupt(task: Task, date: LocalDate)
    fun cancelTaskNotificationInterrupt(task: Task)
}
