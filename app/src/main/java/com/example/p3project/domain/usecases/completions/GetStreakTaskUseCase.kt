package com.example.p3project.domain.usecases.completions

import com.example.p3project.domain.model.Task
import com.example.p3project.domain.model.TaskWithCompletions
import com.example.p3project.domain.repository.TaskRepository
import java.time.LocalDate

class GetStreakTaskUseCase (private val taskRepository: TaskRepository) {
    suspend operator fun invoke(task: Task,
                                from: LocalDate
    ) : TaskWithCompletions {
        return taskRepository.getCompletions(task.taskId, task.periodsPassed(from))!!
    }
}