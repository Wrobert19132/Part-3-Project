package com.example.p3project.sources.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class TaskCompletion (
    val taskId: Int,
 //yes   @Relation(
 //       parentColumn = "taskId",
 //       entityColumn = "id"
//    )
//    val task: Task,


    @PrimaryKey val id: Int,

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    val completionTime: String
) {

}