package com.example.p3project.sources.data;

import com.example.p3project.sources.data.database.Task;

interface InterruptScheduler {
    fun scheduleTaskInterrupt(task: Task)
    fun cancelTaskInterrupt(task: Task)
}
