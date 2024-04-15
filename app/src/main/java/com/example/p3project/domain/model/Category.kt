package com.example.p3project.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Category (
    val categoryName: String,
    val categoryColor: Color,
) {
    @PrimaryKey(autoGenerate = true)
    val categoryId: Int = 0
}



