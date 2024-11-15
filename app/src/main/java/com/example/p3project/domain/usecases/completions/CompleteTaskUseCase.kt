package com.example.p3project.domain.usecases.completions

import com.example.p3project.domain.model.Task
import com.example.p3project.domain.model.Completion
import com.example.p3project.domain.repository.TaskRepository
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime

class CompleteTaskUseCase (private val taskRepository: TaskRepository) {
    suspend operator fun invoke(task: Task,
                                period: Int,
                                completionTime: LocalDateTime
    ) {

        val completion = Completion(task.taskId, period, completionTime)
        taskRepository.addCompletion(completion)
    }
}