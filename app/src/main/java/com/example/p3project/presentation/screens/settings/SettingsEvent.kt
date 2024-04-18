package com.example.p3project.presentation.screens.settings

import androidx.compose.ui.graphics.Color
import com.example.p3project.domain.model.Category

sealed class SettingsEvent {
    data class ViewCategory(val category: Category) : SettingsEvent()
    data class CreateCategory(val categoryName: String, val categoryColor: Color) : SettingsEvent()
    data object OpenCreateCategoryDialog : SettingsEvent()
    data object ReloadCategories: SettingsEvent()
    data object CloseDialog: SettingsEvent()
    data class DeleteCategory(val category: Category): SettingsEvent()

}
