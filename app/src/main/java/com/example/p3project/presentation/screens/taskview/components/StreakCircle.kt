package com.example.p3project.presentation.screens.taskview.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.p3project.domain.model.TaskWithRelations
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun StreakCircle(taskInfo: TaskWithRelations, from: LocalDate) {
    val now = LocalDateTime.now()

    val task = taskInfo.task
    val streak = taskInfo.streakFrom(from)

    val progress = if (task.minutesUntilTask(now) <= 0) {
        1f
    } else {
        1f - (task.minutesUntilTask(now).toFloat() / task.periodLengthMinutes().toFloat())
    }

    Box(modifier = Modifier.size(125.dp),
        contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            progress = { progress },
        )

//    Text(
//        text=taskInfo.streakCount(from).toString()
//    )
        Column {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(),
            ) {
                Column {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = streak.size().toString(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.displayMedium,
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "point streak",
                        overflow = TextOverflow.Visible,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }

    }
}