package com.example.p3project.presentation.screens.sharedComponents

import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.p3project.domain.model.TaskWithRelations
import java.time.LocalDateTime


@Composable
fun TaskCompletionButton(taskInfo: TaskWithRelations,
                         modifier: Modifier = Modifier,
                         onComplete: (() -> Unit),
                         onUncomplete: (() -> Unit)) {
    val task = taskInfo.task
    val now = LocalDateTime.now()

    val isEnabled = task.isTaskDay(now.toLocalDate()) && !taskInfo.completedToday(now.toLocalDate())

    var colors = ButtonDefaults.filledTonalButtonColors()

    if (!isEnabled) {
        colors = colors.copy(containerColor = colors.disabledContainerColor,
            contentColor = colors.disabledContentColor
        )
    }

    val isTaskDay = task.isTaskDay(now.toLocalDate())

    FilledTonalButton(
        colors = colors,
        enabled = true,
        onClick = { if (isEnabled) {onComplete()} else if (isTaskDay) {onUncomplete()} },
        modifier = modifier
            .width(130.dp)
        ) {
        if (isTaskDay) {
            if (taskInfo.completedToday(now.toLocalDate())) {
                Text("Completed!")
            } else {
                Text("Complete")

            }
        } else {
            TaskTime(task = task, long = false)
        }
    }
}