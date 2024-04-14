package com.example.p3project.domain.model

import android.graphics.ColorSpace
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime

@Entity
class TaskCategory (
    @PrimaryKey
    val id: String,
    val categoryName: String,
    val categoryColor: Color,
    ) {
}


@Entity(primaryKeys = ["taskId", "categoryId"])
data class TaskCategoryCrossRef(
    val taskId: Long,
    val categoryId: Long
)
