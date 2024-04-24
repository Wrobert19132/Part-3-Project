package com.example.p3project.presentation.screens.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p3project.domain.model.Category
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.domain.usecases.UseCases
import com.example.p3project.domain.util.TaskViewMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class OverviewViewmodel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    public val state = MutableStateFlow(
        OverviewState(listOf(), TaskViewMode.IncompleteView)
    )

    fun onEvent(event: OverviewEvent) {
         when (event) {
             is OverviewEvent.ReloadInfo -> {
                 viewModelScope.launch(Dispatchers.IO) {
                     getInfo()
                 }
             }

             is OverviewEvent.CompleteTask -> {
                 viewModelScope.launch(Dispatchers.IO) {
                     completeTask(event.task)
                 }
             }

             is OverviewEvent.UpdateViewMode -> {
                 viewModelScope.launch(Dispatchers.IO) {
                     updateViewMode(event.viewMode)
                 }
             }

             is OverviewEvent.ToggleCategory -> viewModelScope.launch(Dispatchers.IO) {
                 toggleCategoryFilter(event.category)
             }

             is OverviewEvent.UncompleteTask -> viewModelScope.launch(Dispatchers.IO) {
                 uncompleteTask(event.taskInfo)
             }
         }
    }
    private suspend fun completeTask(task: Task) {

        useCases.completeTasksUseCase(task,
                                      task.periodsPassed(task.nextTaskDay(LocalDate.now())),
                                      LocalTime.now()
        )

        getInfo()
    }

    private suspend fun uncompleteTask(taskInfo: TaskWithRelations) {

        useCases.uncompleteTasksUseCase(taskInfo,
            taskInfo.task.periodsPassed(LocalDate.now()),
        )

        getInfo()
    }

    private suspend fun updateViewMode(mode: TaskViewMode) {
        state.value = state.value.copy(viewMode = mode)
        getInfo()
    }

    private suspend fun toggleCategoryFilter(category: Category) {
        if (category.categoryId in state.value.categoryFilters) {
            state.value = state.value.copy(
                categoryFilters = state.value.categoryFilters.minus(category.categoryId)
            )
        } else {
            state.value = state.value.copy(
                categoryFilters = state.value.categoryFilters.plus(category.categoryId)
            )
        }
        getInfo()
    }

    private suspend fun getInfo() {
        val freshCategories = useCases.allCategoriesUseCase()
        val freshCategoryIds = freshCategories.map { it.categoryId }

        val removedCategoryIds: List<Int> = state.value.categories.filter {
            category -> (category.categoryId !in freshCategoryIds)
        }.map { it.categoryId }

        state.value = state.value.copy(
            tasksInfo = useCases.getTasksUseCase(
                state.value.viewMode,
                state.value.categories.filter {
                    it.categoryId in state.value.categoryFilters
                },
                onlyEnabled = (state.value.viewMode !is TaskViewMode.AllView)
            ),
            categories = freshCategories,
            categoryFilters = state.value.categoryFilters.filter { it !in removedCategoryIds }.toSet()
        )
    }
}