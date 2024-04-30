package com.example.p3project.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.p3project.domain.model.Category
import com.example.p3project.domain.model.CategoryCount
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.model.Completion
import com.example.p3project.domain.model.TaskCategoryCrossRef
import com.example.p3project.domain.model.TaskWithRelations

@Dao
interface TasksDao {

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(task: Task)

    @Query("SELECT category.categoryId, categoryName, categoryColor, COUNT(*) as count FROM " +
            "TaskCategoryCrossRef " +
            "INNER JOIN category ON category.categoryId=taskcategorycrossref.categoryId " +
            "GROUP BY category.categoryId"
    )
    suspend fun categoryUsage(): List<CategoryCount>




    @Transaction
    @Query("SELECT * FROM task WHERE taskId=:id")
    fun getTaskInfo(id: Int): TaskWithRelations?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addTask(task: Task): Long
    @Transaction
    @Query("SELECT * FROM task " +
            "INNER JOIN TaskCategoryCrossRef ON task.taskId=TaskCategoryCrossRef.taskId " +
            "WHERE categoryId in (:categories) GROUP BY task.taskId ORDER BY targetTime")
    suspend fun getFilteredTaskInfo(categories: List<Int>): List<TaskWithRelations>

    @Transaction
    @Query("SELECT * FROM task ORDER BY targetTime")
    suspend fun getAllTaskInfo(): List<TaskWithRelations>


    @Delete
    suspend fun deleteTask(task: Task)

    @Query("DELETE FROM TaskCategoryCrossRef WHERE taskId=:taskId")
    suspend fun deleteTaskCategories(taskId: Int)

    @Query("DELETE FROM completion WHERE taskId=:taskId")
    suspend fun deleteTaskCompletions(taskId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCompletion(completion: Completion)
    @Query("DELETE FROM completion WHERE period=:period AND taskId=:taskId")
    suspend fun deleteCompletion(period: Int, taskId: Int)





    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createCategory(category: Category): Long
    @Delete()

    suspend fun deleteCategory(category: Category)

    @Query("DELETE FROM TaskCategoryCrossRef WHERE categoryId=:categoryId")
    suspend fun unassignAllCategory(categoryId: Int)



    @Query("SELECT * FROM category")
    suspend fun getAllCategories(): List<Category>


    @Query("DELETE FROM TaskCategoryCrossRef WHERE taskId=:taskId AND categoryId=:categoryId")
    suspend fun unassignCategory(taskId: Int, categoryId: Int)

    @Insert(TaskCategoryCrossRef::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun assignCategory(assignment: TaskCategoryCrossRef)




}