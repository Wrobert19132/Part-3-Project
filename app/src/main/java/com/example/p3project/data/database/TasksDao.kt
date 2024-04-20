package com.example.p3project.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.p3project.domain.model.Category
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.model.Completion
import com.example.p3project.domain.model.TaskWithRelations

@Dao
interface TasksDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addTask(task: Task): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(task: Task)

    @Transaction
    @Query("SELECT * FROM task WHERE taskId=:id")
    fun getTaskInfo(id: Int): TaskWithRelations?

    @Transaction
    @Query("SELECT * FROM task " +
            "INNER JOIN TaskCategoryCrossRef ON task.taskId=TaskCategoryCrossRef.taskId " +
            "WHERE categoryId in (:categories)")
    suspend fun getFilteredTaskInfo(categories: List<Int>): List<TaskWithRelations>

    @Transaction
    @Query("SELECT * FROM task")
    suspend fun getAllTaskInfo(): List<TaskWithRelations>


    @Delete
    suspend fun deleteTask(task: Task)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCompletion(completion: Completion)
    @Delete()
    suspend fun deleteCompletion(completion: Completion)





    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createCategory(category: Category): Long
    @Delete()

    suspend fun deleteCategory(category: Category)

    @Query("DELETE FROM TaskCategoryCrossRef WHERE categoryId=:categoryId")
    suspend fun unassignAllCategory(categoryId: Int)



    @Query("SELECT * FROM category")
    suspend fun getAllCategories(): List<Category>


    @Query("INSERT INTO TaskCategoryCrossRef VALUES (:taskId, :categoryId)")
    suspend fun assignCategory(taskId: Int, categoryId: Int)

    @Query("DELETE FROM TaskCategoryCrossRef WHERE taskId=:taskId AND categoryId=:categoryId")
    suspend fun unassignCategory(taskId: Int, categoryId: Int)




}