package com.example.p3project.domain.model;

import androidx.room.Embedded;

data class CategoryCount(
    @Embedded val category: Category,
    val count: Int,
)
