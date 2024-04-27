package com.example.p3project.domain.usecases.charts

import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.p3project.domain.model.Completion
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.util.CompletionTimeCategory

class BuildCompletionDataUseCase {
    operator fun invoke(task: Task, completions: List<Completion>): PieChartData {
        val categoryCompletions = completions.groupBy {
            it.getCategory(
                task.dateTimeForPeriod(it.period), task.notificationOffset
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

        return PieChartData(
            categoryUsages.mapValues { (category, value) ->
                PieChartData.Slice(category.name, value.toFloat(), category.color)
            }.values.toList(),
            PlotType.Pie
        )
    }
}