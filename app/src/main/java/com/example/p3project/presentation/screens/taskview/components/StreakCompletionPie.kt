package com.example.p3project.presentation.screens.taskview.components

import android.graphics.Typeface
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import co.yml.charts.common.components.Legends
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.p3project.domain.model.Streak
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.util.CompletionTimeCategory

@Composable
fun StreakCompletionPie(streak: Streak, task: Task) {


    ElevatedCard() {
        Text(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            text = "Current Streak Completion Ratio",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
        if (streak.size() == 0) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .wrapContentSize(),
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Not enough data - start making some completions!",
                    overflow = TextOverflow.Visible,
                    textAlign = TextAlign.Center,
                )
            }
        } else {

            val categoryCompletions = streak.completions.groupBy {
                it.getCategory(
                    task,
                )
            }
            val categoryUsages = mapOf(
                CompletionTimeCategory.LateComplete to
                    (categoryCompletions[CompletionTimeCategory.LateComplete] ?: listOf()).size,
                CompletionTimeCategory.OnTimeComplete to
                        (categoryCompletions[CompletionTimeCategory.OnTimeComplete] ?: listOf()).size,
                CompletionTimeCategory.EarlyComplete to
                        (categoryCompletions[CompletionTimeCategory.EarlyComplete] ?: listOf()).size,
            )

            val data = PieChartData(
                    categoryUsages.mapValues { (category, value) ->
                        PieChartData.Slice(category.name, value.toFloat(), category.color)
                    }.values.toList(),
            PlotType.Pie
            )


            val pieChartConfig = PieChartConfig(
                labelVisible = true,
                strokeWidth = 120f,
                labelColor = Color.Black,
                activeSliceAlpha = .9f,
                labelTypeface = Typeface.defaultFromStyle(Typeface.BOLD),
                backgroundColor = Color.Transparent,
                chartPadding = 25,
                isClickOnSliceEnabled = false,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
            ) {
                DonutPieChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    data,
                    pieChartConfig
                ) { slice ->

                }
                Legends(
                    legendsConfig = DataUtils.getLegendsConfigFromPieChartData(
                        pieChartData = data,
                        3
                    )
                )
            }
        }
    }
}