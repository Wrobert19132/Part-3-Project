package com.example.p3project.domain.usecases.completions

import com.example.p3project.domain.model.Completion
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.domain.repository.TaskRepository
import java.time.chrono.ChronoPeriod

class UncompleteTaskUseCase (private val taskRepository: TaskRepository) {
    suspend operator fun invoke(task: TaskWithRelations, period: Int) {
        for (completion in task.completions) {
            if (completion.period == period) {
                taskRepository.deleteCompletion(completion)
            }
        }
    }
}