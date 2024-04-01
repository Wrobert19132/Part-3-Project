package com.example.p3project.sources.presentation.screens.overview.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.p3project.sources.data.database.Task
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun TaskCard (task: Task, onClick: () -> Unit) {

    val now = LocalDateTime.now()
    var minutesUntil by remember {
        mutableLongStateOf(task.minutesUntilTask(LocalDateTime.now()))
    }

    LaunchedEffect(minutesUntil) {
        if (minutesUntil >= 0) {
            delay(1000 * 60)
            minutesUntil -= 1
        }
    }
    if (minutesUntil >= 0) {

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

                    if (task.isTaskDay(now.toLocalDate())) {
                        if (minutesUntil < 60) {
                            Text(
                                text = "$minutesUntil minutes away",
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.tertiary,
                                textAlign = TextAlign.Right,
                            )
                        } else {
                            Text(
                                text = String.format("%02d:%02d", task.time.hour, task.time.minute),
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.tertiary,
                                textAlign = TextAlign.Right,
                            )
                        }
                    } else {
                        Text(
                            text = task.daysUntilNextTaskDay(now.toLocalDate())
                                .toString() + " days away",
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Right
                        )
                    }


                    // Debug
                    Text(
                        text = task.id.toString(),
                        modifier = Modifier.fillMaxWidth(),
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Magenta,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }

                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(10.dp))
                FilledTonalButton(
                    onClick = { /*TODO*/ },
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