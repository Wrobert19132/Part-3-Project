package com.example.p3project.presentation.screens.settings;

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p3project.domain.model.Category
import com.example.p3project.domain.usecases.UseCases;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewmodel @Inject constructor(private val useCases: UseCases): ViewModel() {
    public val state = MutableStateFlow(
        SettingsState()
    )

    private fun viewCategory(category: Category) {
        state.value = state.value.copy(selectedCategory = category)
    }


    private suspend fun getCategories() {
        state.value = state.value.copy(categories = useCases.allCategoriesUseCase())
    }

    private suspend fun createCategory(categoryName: String, categoryColor: Color) {
        useCases.createCategoryUseCase(categoryName, categoryColor)
        getCategories()
        closeDialog()
    }

    private suspend fun deleteCategory(category: Category) {
        useCases.deleteCategoryUseCase(category)
        getCategories()
        closeDialog()
    }

    private fun openCreateCategoryDialog() {
        state.value = state.value.copy(creatingCategory = true)
    }

    private fun closeDialog() {
        state.value = state.value.copy(selectedCategory = null, creatingCategory = false)
    }

    fun onEvent(event: SettingsEvent) {
        if (event is SettingsEvent.ViewCategory) {
            viewCategory(event.category)
        } else if (event is SettingsEvent.CloseDialog) {
            closeDialog()
        } else if (event is SettingsEvent.OpenCreateCategoryDialog) {
            openCreateCategoryDialog()
        } else if (event is SettingsEvent.CreateCategory) {
            viewModelScope.launch(Dispatchers.IO) {
                createCategory(event.categoryName, event.categoryColor)
            }
        } else if (event is SettingsEvent.ReloadCategories) {
            viewModelScope.launch(Dispatchers.IO) {
                getCategories()
            }
        } else if (event is SettingsEvent.DeleteCategory) {
            viewModelScope.launch(Dispatchers.IO) {
                deleteCategory(event.category)
            }

        }
    }



}
