package com.example.p3project.presentation.screens.overview.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.domain.util.TaskViewMode

@Composable
fun OverviewList(taskInfos: List<TaskWithRelations>,
                 viewMode: TaskViewMode,
                 onTaskClick: ((TaskWithRelations) -> Unit),
                 onTaskComplete: ((TaskWithRelations) -> Unit),
                 onTaskUncomplete: ((TaskWithRelations) -> Unit),
                 ) {
    if (taskInfos.isEmpty()) {
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = when (viewMode) {
                    is TaskViewMode.TodayView -> "No tasks for today!"
                    is TaskViewMode.AllView -> "You have yet to create any tasks."
                    is TaskViewMode.IncompleteView -> "You've done all your tasks for today!"
            }
        )

    } else {

        LazyColumn() {
            items(taskInfos) { taskAndCompletions ->
                val task = taskAndCompletions.task

                TaskCard(
                    taskAndCompletions,
                    onClick = {
                        onTaskClick(taskAndCompletions)
                    },
                    onComplete = {
                        onTaskComplete(taskAndCompletions)
                    },
                    onUncomplete = {
                        onTaskUncomplete(taskAndCompletions)
                    },

                    )
            }
        }
    }
}
