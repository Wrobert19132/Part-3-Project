package com.example.p3project.presentation.screens.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.p3project.domain.model.Category

@Composable
fun CategoryFilterSelector(categories: List<Category>, onSelectCategory: ((category: Category) -> Unit),
                           selectedCategories: Set<Int>) {
    LazyRow(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(categories) { category ->
            FilterChip(
                selected = category.categoryId in selectedCategories,
                onClick = { onSelectCategory(category) },
                label = { Text(category.categoryName) },
            )
        }
    }
}