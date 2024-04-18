package com.example.p3project.presentation.screens.overview.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.p3project.domain.model.Category
import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.presentation.screens.shared_components.StreakCircle
import com.example.p3project.presentation.screens.shared_components.TaskTime
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
private fun CategoryView(categories: List<Category>) {
    LazyRow(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(categories) { category ->
            FilterChip(
                selected = false,
                onClick = {  },
                label = { Text(category.categoryName) },
            )
        }
    }
}

@Composable
fun TaskCard (taskInfo: TaskWithRelations, onClick: () -> Unit, onComplete: () -> Unit) {
    val task = taskInfo.task


    val now = LocalDateTime.now()

    var minutesUntil by remember {
        mutableIntStateOf(task.minutesUntilTask(LocalDateTime.now()))
    }
    var isTaskDay by remember {mutableStateOf(task.isTaskDay(now.toLocalDate()))}

    LaunchedEffect(minutesUntil) {
        if (minutesUntil >= 0) {
            delay(1000 * 60)

            minutesUntil -= 1
            isTaskDay = task.isTaskDay(now.toLocalDate())
        }
    }

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
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        SmallStreakCircle(
                            taskInfo = taskInfo,
                            from = now.toLocalDate()
                        )

                        Text(
                            text = task.name,
                            modifier = Modifier.fillMaxWidth(),
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))

                    CategoryView(categories = taskInfo.categories)

                    TaskTime(
                        task,
                        long = false,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleMedium,
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            FilledTonalButton(
                enabled = isTaskDay && !taskInfo.completedToday(now.toLocalDate()),
                onClick = { onComplete() },
                modifier = Modifier
                    .width(130.dp)
                    .align(Alignment.End),

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
    }
}