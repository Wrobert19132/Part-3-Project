package com.example.p3project.domain.util

import androidx.compose.ui.graphics.Color

sealed class CompletionTimeCategory(val name: String, val color: Color) {
    data object EarlyComplete: CompletionTimeCategory("Early Completion", Color(255, 140, 0))
    data object OnTimeComplete: CompletionTimeCategory("On Time Completion",Color(0, 102, 0))
    data object LateComplete: CompletionTimeCategory("Late Completion", Color(153, 0, 0))
}