package com.example.p3project

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.p3project.data.database.TaskDatabase
import com.example.p3project.data.repository.TaskRepositoryImpl
import com.example.p3project.domain.repository.TaskRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before

open class DBTest {
    lateinit var db: TaskDatabase
    lateinit var repo: TaskRepository

    @Before
    fun createDb() = runTest {
        val context: Context = ApplicationProvider.getApplicationContext()

        db = Room.inMemoryDatabaseBuilder(
            context, TaskDatabase::class.java).build()
        repo = TaskRepositoryImpl(db.tasksDao())
    }

}