package com.example.p3project.services

import com.example.p3project.domain.model.Task
import com.example.p3project.domain.service.InterruptScheduler
import java.time.LocalDateTime

class TestInterruptSchedulerService: InterruptScheduler {
    public val scheduledNotificationTasks: HashMap<Int,Task> = hashMapOf()
    public val scheduledNotificationTimes: HashMap<Int,LocalDateTime> = hashMapOf()

    public val scheduledFollowupTasks: HashMap<Int,Task> = hashMapOf()
    public val scheduledFollowupTimes: HashMap<Int,LocalDateTime> = hashMapOf()

    public val canceledTasks: MutableList<Task> = mutableListOf()

    override fun scheduleTaskNotificationInterrupt(task: Task, date: LocalDateTime) {
        scheduledNotificationTasks[task.taskId] = task
        scheduledNotificationTimes[task.taskId] = date
    }

    override fun scheduleFollowUpNotificationInterrupt(task: Task, date: LocalDateTime) {
        scheduledFollowupTasks[task.taskId] = task
        scheduledFollowupTimes[task.taskId] = date
    }

    override fun cancelTaskNotificationInterrupt(task: Task) {
        canceledTasks.add(task)
        scheduledFollowupTasks.remove(task.taskId)
    }
}