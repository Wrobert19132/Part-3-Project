package com.example.p3project.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category (
    var categoryName: String,
    var categoryColor: Int,

) {
    @PrimaryKey(autoGenerate = true)
    var categoryId: Int = 0

    public fun getColor(): Color {
        return Color(categoryColor)
    }

    companion object Limits {
        var maxNameLength: Int = 8;
    }
}



