package com.example.p3project.domain.util

import androidx.compose.ui.graphics.Color

sealed class CompletionTimeCategory(val name: String, val color: Color) {
    data object EarlyComplete: CompletionTimeCategory("Early Completions", Color(51, 204, 51))
    data object OnTimeComplete: CompletionTimeCategory("On Time Completions",Color(0, 102, 0))
    data object LateComplete: CompletionTimeCategory("Late Completions", Color(153, 0, 0))
}