package com.example.p3project.domain.usecases.tasks

import com.example.p3project.domain.model.Category
import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.domain.repository.TaskRepository
import com.example.p3project.domain.util.TaskViewMode
import java.time.LocalDate
import java.time.LocalDateTime

class GetTasksUseCase (private val taskRepository: TaskRepository)
{
    suspend operator fun invoke(viewMode: TaskViewMode = TaskViewMode.AllView,
                                filters: List<Category> = listOf(),
                                onlyEnabled: Boolean = true,
                                now: LocalDateTime = LocalDateTime.now()
    ) : List<TaskWithRelations> {
        val tasks = taskRepository.allTaskInfo(filters).filter {it.task.enabled || !onlyEnabled }

        return when (viewMode) {
            is TaskViewMode.TodayView ->
                tasks.filter {taskInfo -> (taskInfo.task.isTaskDay(now))}
            is TaskViewMode.AllView ->
                tasks
            is TaskViewMode.IncompleteView ->
                tasks.filter {
                    taskInfo -> (taskInfo.task.isTaskDay(now) && !taskInfo.completedOnDay(taskInfo.task.nextTaskDay(now.toLocalDate())))
                }
        }
    }
}

