package com.example.p3project.domain.usecases.completions

import com.example.p3project.domain.model.Task
import com.example.p3project.domain.model.Completion
import com.example.p3project.domain.repository.TaskRepository
import java.time.LocalDateTime
import java.time.OffsetDateTime

class CompleteTaskUseCase (private val taskRepository: TaskRepository) {
    suspend operator fun invoke(task: Task,
                                period: Int,
                                completionTime: LocalDateTime
    ) {
        val offset = OffsetDateTime.now().offset

        val completionSecondsBefore =
            task.timeForPeriod(period).toEpochSecond(offset) - completionTime.toEpochSecond(offset)

        val completion = Completion(task.taskId, period, completionSecondsBefore.toInt())
        taskRepository.addCompletion(completion)
    }
}