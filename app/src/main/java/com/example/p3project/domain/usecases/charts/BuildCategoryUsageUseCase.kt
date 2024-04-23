package com.example.p3project.domain.usecases.charts

import androidx.compose.ui.graphics.Color
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.p3project.domain.repository.TaskRepository
import kotlin.math.max
import kotlin.math.min

class BuildCategoryUsageUseCase(val repository: TaskRepository) {
    suspend operator fun invoke(): PieChartData {
        val colors = mutableListOf(
            Color(186,255,201),
            Color(160,124, 167),
            Color(186,225,255),
            Color(255,179,186),
            Color(255,223,186),
            Color(255,255,186)
        )
        val usage = repository.categoryUsage()
        return PieChartData(
            usage.sortedBy { it.count }
                .subList(fromIndex = 0, toIndex = min(colors.size, usage.size))
                .map {categoryCount ->
                PieChartData.Slice(categoryCount.category.categoryName,
                    categoryCount.count.toFloat(), colors.removeAt(0)
                )
            },
            PlotType.Pie
        )
    }
}