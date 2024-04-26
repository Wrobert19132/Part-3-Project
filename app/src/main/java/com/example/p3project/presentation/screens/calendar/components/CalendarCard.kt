package com.example.p3project.presentation.screens.calendar.components

import androidx.compose.runtime.Composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import co.yml.charts.common.extensions.isNotNull
import com.example.p3project.domain.model.TaskWithRelations
import java.time.DateTimeException
import java.time.LocalDate

@Composable
fun CalendarDay(date: LocalDate, onClick: (() -> Unit),
                tasks: List<TaskWithRelations>) {


    val colorScheme = MaterialTheme.colorScheme
    val today = LocalDate.now()

    Column (
        Modifier
            .fillMaxSize()
            .clickable { onClick() }
    ){
        Box(Modifier.padding(5.dp)) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .drawBehind {
                        if (date == today) {
                            drawCircle(colorScheme.primary, 25f)
                        }
                    },
                color = if (date == today) {
                    colorScheme.onPrimary
                } else {
                    colorScheme.onPrimaryContainer
                },
                text = date.dayOfMonth.toString(),
                style = MaterialTheme.typography.bodyMedium,
            )
        }


        LazyColumn (
            Modifier.padding(5.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ){
            for (taskInfo in tasks) {
                val task = taskInfo.task

                val completionForDay = taskInfo.completionForDay(date)

                item {
                    Text(
                        text = task.name,
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = if (task.startDate == date) {
                            FontWeight.Bold
                        } else {
                            FontWeight.Normal
                        },
                        color = if (completionForDay.isNotNull()) {
                            val completionCategory = completionForDay!!.getCategory(task.timeForPeriod(completionForDay.period), task.notificationOffset)

                            completionCategory.color
                        } else {
                            Color.Gray
                        }
                    )
                }
            }
        }


    }

}

@Composable
fun CalendarCard(date: LocalDate,
                 tasks: List<TaskWithRelations>,
                 selectDay: ((day: LocalDate) -> Unit),
                 navigateLeft:  (() -> Unit),
                 navigateRight: (() -> Unit)
) {
    val monthLength: Int = date.month.maxLength()
    val monthDays: MutableList<LocalDate> = (1..monthLength)
        .map { day ->
            try {
                LocalDate.of(date.year, date.month, day)
            } catch (_: DateTimeException) {
                null
            }
        }.filter {it.isNotNull()}.map { it!! }
        .toMutableList()

    Card {

        Column(
            Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
                text = "${date.month.name.lowercase().replaceFirstChar { it.uppercase() }}, ${date.year}",
                textAlign = TextAlign.Center)

            Box(
                modifier = Modifier
                    .border(BorderStroke(2.dp, MaterialTheme.colorScheme.onBackground))
                    .align(Alignment.CenterHorizontally)) {
                Column {
                    repeat(6) {
                        Row() {
                            repeat(7) { dayOfWeek ->
                                Box(
                                    Modifier
                                        .width(52.dp)
                                        .height(80.dp)
                                        .border(
                                            BorderStroke(
                                                1.dp,
                                                MaterialTheme.colorScheme.onBackground
                                            )
                                        )
                                ) {
                                    if (monthDays.isNotEmpty() && monthDays[0].dayOfWeek.value == dayOfWeek + 1) {
                                        val day = monthDays.removeAt(0)

                                        CalendarDay(day,
                                            {selectDay(day)},
                                            tasks.filter { taskInfo -> taskInfo.task.isTaskDay(day) }, )
                                    }

                                }
                            }
                        }
                    }
                }
            }

            Row(Modifier.align(Alignment.End)
            ) {
                IconButton(
                    onClick = navigateLeft) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Previous Month")
                }
                IconButton(
                    onClick = navigateRight) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, "Next Month")
                }
            }
        }
    }
}