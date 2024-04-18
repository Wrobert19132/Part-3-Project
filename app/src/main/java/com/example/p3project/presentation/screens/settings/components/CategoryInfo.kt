package com.example.p3project.presentation.screens.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.p3project.domain.model.Category


@Composable
fun CategoryInfo(categories: List<Category>,
                 onPickCategory: ((category: Category) -> Unit), onAddMore: (() -> Unit)) {
    ListItem(leadingContent = {Text("Your Categories:")},
            headlineContent = {
            LazyRow(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(categories) { category ->
                    FilterChip(
                        selected = false,
                        onClick = { onPickCategory(category) },
                        label = { Text(category.categoryName) },
                    )
                }

                item {
                    AssistChip(
                        onClick = onAddMore,
                        label = { Text("Add more...") },
                    )
                }
            }
        }
    )
}