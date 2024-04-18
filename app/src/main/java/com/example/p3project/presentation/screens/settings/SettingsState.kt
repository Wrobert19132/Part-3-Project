package com.example.p3project.presentation.screens.settings

import com.example.p3project.domain.model.Category

data class SettingsState(
    var selectedCategory: Category? = null,
    var creatingCategory: Boolean = false,
    var categories: List<Category> = listOf()
)