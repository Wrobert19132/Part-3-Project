package com.example.p3project.presentation.screens.sharedComponents

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.example.p3project.domain.model.Task
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import kotlin.math.abs

private fun getText(task: Task, now: LocalDateTime, long: Boolean): String {
    val minutesUntil: Int = task.minutesUntilTask(now)

    val hours = abs(minutesUntil).floorDiv(60)
    val hourMinutes = abs(minutesUntil) % 60

    return if (task.isTaskDay(now.toLocalDate())) {
        if (minutesUntil < 0) {
            if (minutesUntil >= -60) {
                "Today, $hourMinutes ${if (long) "minutes" else "mins"} ago"
            } else {
                "Today,  $hours ${if (long) "hours" else "hrs"}, $hourMinutes ${if (long) "minutes" else "mins"} ago"
            }
        } else {
            if (minutesUntil <= 60) {
                "Today, in $hourMinutes ${if (long) "minutes" else "mins"}"
            } else {
                "Today, in $hours ${if (long) "hours" else "hrs"}, $hourMinutes ${if (long) "minutes" else "mins"}"
            }
        }
    } else {
        "${task.daysUntilNextTaskDay(now.toLocalDate())} days away"
    }
}

@Composable
fun TaskTime(task: Task,
             modifier: Modifier = Modifier,
             long: Boolean = true,
             style: TextStyle = LocalTextStyle.current,
             textAlign: TextAlign? = null
) {
    val now: LocalDateTime = LocalDateTime.now()

    var minutesUntil by remember {
        mutableIntStateOf(task.minutesUntilTask(now))
    }

    LaunchedEffect(minutesUntil) {
        delay(1000 * 60)
        minutesUntil -= 1
    }
    
    Text(text = getText(task, now, long),
         modifier = modifier,
         style = style,
         textAlign = textAlign
    )
}