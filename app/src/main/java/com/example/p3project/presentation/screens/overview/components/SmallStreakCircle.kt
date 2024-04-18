package com.example.p3project.presentation.screens.overview.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
fun SmallStreakCircle(taskInfo: TaskWithRelations, from: LocalDate) {
    val now = LocalDateTime.now()

    val task = taskInfo.task

    val progress = if (task.minutesUntilTask(now) <= 0) {
        1f
    } else {
        1f - (task.minutesUntilTask(now).toFloat() / task.periodLengthMinutes().toFloat())
    }

    Box(modifier = Modifier.size(40.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            progress = { progress },
            strokeWidth = 4.dp,
            trackColor = MaterialTheme.colorScheme.secondaryContainer,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )

//    Text(
//        text=taskInfo.streakCount(from).toString()
//    )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(),
            ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = taskInfo.streakCount(now.toLocalDate()).toString(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }


    }
}