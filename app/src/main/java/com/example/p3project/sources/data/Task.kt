package com.example.p3project.sources.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task (
    var name: String,
    var description: String,
    var hour: Int,
    var minute: Int,
    var dayInterval: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}