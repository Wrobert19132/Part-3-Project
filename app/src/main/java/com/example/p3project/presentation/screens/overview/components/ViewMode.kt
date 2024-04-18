package com.example.p3project.presentation.screens.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.p3project.domain.util.TaskViewMode

@Composable
fun ViewMode(mode: TaskViewMode, pickView: ((TaskViewMode) -> Unit),) {
    Row (
        Modifier.padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)

    ) {
        FilterChip(selected = mode == TaskViewMode.IncompleteView,
                   onClick = {pickView(TaskViewMode.IncompleteView)},
                   label = { Text(text = "To Do")}
        )

        FilterChip(selected = mode == TaskViewMode.TodayView,
            onClick = {pickView(TaskViewMode.TodayView)},
            label = { Text(text = "Today")}
        )

        FilterChip(selected = mode == TaskViewMode.AllView,
            onClick = {pickView(TaskViewMode.AllView)},
            label = { Text(text = "All")}
        )


    }
}