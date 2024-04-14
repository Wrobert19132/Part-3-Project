package com.example.p3project.presentation.screens.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.p3project.domain.util.OverviewMode
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ViewMode(mode: OverviewMode, pickTodayView: (() -> Unit), pickAllView: (() -> Unit),) {
    Row (
        Modifier.padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)

    ) {
        FilterChip(selected = mode == OverviewMode.TodayView,
                   onClick = pickTodayView,
                   label = { Text(text = "Today's Tasks")})

        FilterChip(selected = mode == OverviewMode.AllView,
            onClick = pickAllView,
            label = { Text(text = "All Tasks")})
    }
}