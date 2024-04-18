package com.example.p3project.domain.usecases.tasks

import com.example.p3project.domain.model.Category
import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.domain.repository.TaskRepository
import com.example.p3project.domain.util.TaskViewMode
import java.time.LocalDate

class AllTaskInfoUseCase (private val taskRepository: TaskRepository)
{
    suspend operator fun invoke(viewMode: TaskViewMode = TaskViewMode.TodayView,
                                filters: List<Category> = listOf()
    ) : List<TaskWithRelations> {
        val tasks = taskRepository.allTaskInfo(filters)
        val now = LocalDate.now()

        return when (viewMode) {
            is TaskViewMode.TodayView ->
                tasks.filter {taskInfo -> (taskInfo.task.isTaskDay(now)) }
            is TaskViewMode.AllView ->
                tasks
            is TaskViewMode.IncompleteView ->
                tasks.filter { taskInfo -> (taskInfo.task.isTaskDay(now) && !taskInfo.completedToday(now)) }
        }
    }
}