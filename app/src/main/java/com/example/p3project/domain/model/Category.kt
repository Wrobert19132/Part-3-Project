package com.example.p3project.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Category (
    @PrimaryKey
    val categoryId: Int,
    val categoryName: String,
    val categoryColor: String,
)



