package com.example.p3project.presentation.screens.addtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p3project.domain.model.Category
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.usecases.UseCases
import com.example.p3project.domain.util.InvalidTaskException
import com.kotlinx.extend.isInt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AddTaskScreenViewmodel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {



    public val state = MutableStateFlow(
        AddTaskState()
    )

    private suspend fun addTask() {
        try {
            val stateVal = state.value

            if (stateVal.notificationOffset == null) {
                throw InvalidTaskException("You have not entered a valid notification time.")
            }

            if (stateVal.taskInterval == null) {
                throw InvalidTaskException("You have not entered a valid task interval.")
            }

            val task = useCases.addTaskUseCase(stateVal.taskName,
                                               stateVal.taskDescription,
                                               stateVal.targetTime, stateVal.startDate,
                                               stateVal.notificationOffset!!,
                                               stateVal.taskInterval!!
                                        )

            for (category in stateVal.appliedCategories) {
                useCases.assignCategoryUseCase(task, category)
            }

            useCases.scheduleTaskUseCase(task)

            state.value = state.value.copy(taskAdded = true)
        } catch (e: InvalidTaskException) {
            onEvent(AddTaskEvent.SendError(e.message!!))
        }
    }

    private fun setTaskName(name: String) {
        if (name.length <= Task.maxNameLength) {
            state.value = state.value.copy(taskName = name)
        }
    }

    private fun setTaskDescription(description: String) {
        if (description.length <= Task.maxDescriptionLength) {
            state.value = state.value.copy(taskDescription = description)
        }
    }

    private fun setTaskInterval(interval: String) {
        if (interval.isInt()) { // Check for empty string
            state.value = state.value.copy(
                taskInterval = minOf(interval.toInt(), 999)
            )
        } else {
            state.value = state.value.copy(taskInterval = null)
        }
    }

    private fun setTaskStartDate(startDate: LocalDate) {
        state.value = state.value.copy(startDate = startDate)
    }

    private fun setTaskTargetTime(targetTime: LocalTime) {
        state.value = state.value.copy(targetTime = targetTime)
    }

    private fun setNotificationOffset(offset: String) {
        if (offset.isInt()) {
            state.value = state.value.copy(notificationOffset = maxOf(minOf(offset.toInt(), 1440), 0))
        } else {
            state.value = state.value.copy(notificationOffset = null)
        }
    }

    private fun toggleCategory(category: Category) {
        if (category in state.value.appliedCategories) {
            state.value = state.value.copy(
                appliedCategories = state.value.appliedCategories.minus(category)
            )
        } else {
            state.value = state.value.copy(
                appliedCategories = state.value.appliedCategories.plus(category)
            )
        }
    }

    private fun dismissError() {
        state.value = state.value.copy(error=null)
    }

    private fun createError(error: String) {
        state.value = state.value.copy(error=error)
    }

    private suspend fun loadTaskCategories() {
        state.value = state.value.copy(allCategories = useCases.allCategoriesUseCase(),
                                       appliedCategories = setOf()
        )
    }

    fun onEvent(event: AddTaskEvent) {
        when (event) {
            is AddTaskEvent.AddTask ->
            viewModelScope.launch(Dispatchers.IO) {
                addTask()
            }

            AddTaskEvent.LoadCategories -> viewModelScope.launch(Dispatchers.IO) {
                loadTaskCategories()
            }

            is AddTaskEvent.DismissError -> dismissError()
            is AddTaskEvent.SendError -> createError(event.message)

            is AddTaskEvent.SetTargetTime -> setTaskTargetTime(event.targetTime)
            is AddTaskEvent.SetDescription -> setTaskDescription(event.description)
            is AddTaskEvent.SetInterval -> setTaskInterval(event.interval)
            is AddTaskEvent.SetName -> setTaskName(event.name)
            is AddTaskEvent.SetStartDate -> setTaskStartDate(event.startDate)
            is AddTaskEvent.SetNotificationOffset -> setNotificationOffset(event.offset)


            is AddTaskEvent.ToggleCategory -> toggleCategory(event.category)

        }


    }
}
