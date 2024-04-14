package com.example.p3project.presentation.screens.overview.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.presentation.screens.shared_components.StreakCircle
import com.example.p3project.presentation.screens.shared_components.TaskTime
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun TaskCard (taskAndCompletions: TaskWithRelations, onClick: () -> Unit, onComplete: () -> Unit) {
    val task = taskAndCompletions.task


    val now = LocalDateTime.now()
    var minutesUntil by remember {
        mutableIntStateOf(task.minutesUntilTask(LocalDateTime.now()))
    }

    LaunchedEffect(minutesUntil) {
        if (minutesUntil >= 0) {
            delay(1000 * 60)
            minutesUntil -= 1
        }
    }
    if (true) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 100.dp)
                .padding(2.dp),
            onClick = onClick,
        )

        {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = task.name,
                        modifier = Modifier.fillMaxWidth(),
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium,
                    )

                    StreakCircle(taskWithRelations = taskAndCompletions,
                                 from = now.toLocalDate()
                    )

                    TaskTime(task,
                             long = false,
                             modifier = Modifier.fillMaxWidth(),
                             style = MaterialTheme.typography.titleMedium,
                             textAlign = TextAlign.Right
                    )
                }

                Text(
                    text = task.description + taskAndCompletions.streakCount(LocalDate.now()),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(10.dp))
                FilledTonalButton(
                    onClick = {onComplete()},
                    modifier = Modifier
                        .width(120.dp)
                        .align(Alignment.End),

                    ) {
                    Text(text = "Complete")
                }

            }
        }
    }
}