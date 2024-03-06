package com.example.p3project.sources.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task (
    val name: String,
    @PrimaryKey val id: Int
) {

}