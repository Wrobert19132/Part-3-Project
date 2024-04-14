package com.example.p3project.presentation.screens.shared_components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color.Companion.Red
import com.example.p3project.domain.model.TaskWithCompletions
import java.time.LocalDate

@Composable
fun StreakCircle(taskWithCompletions: TaskWithCompletions, from: LocalDate) {
    Text(
        modifier = Modifier.drawBehind {
                                       drawCircle(color = Red,
                                                  radius = size.maxDimension
                                       )
        },

        text=taskWithCompletions.streakCount(from).toString())
}