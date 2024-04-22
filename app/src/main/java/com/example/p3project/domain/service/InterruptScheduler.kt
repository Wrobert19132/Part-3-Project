package com.example.p3project.domain.service;

import com.example.p3project.domain.model.Task;
import java.time.LocalDate
import java.time.LocalDateTime

interface InterruptScheduler {
    fun scheduleTaskNotificationInterrupt(task: Task, date: LocalDateTime)
    fun scheduleFollowUpNotificationInterrupt(task: Task, date: LocalDateTime)
    fun cancelTaskNotificationInterrupt(task: Task)
}
