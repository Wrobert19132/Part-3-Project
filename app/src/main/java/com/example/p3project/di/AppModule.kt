package com.example.p3project.di

import android.app.Application
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.room.Room
import com.example.p3project.sources.data.InterruptScheduler
import com.example.p3project.sources.data.InterruptSchedulerImpl
import com.example.p3project.sources.data.database.TaskDatabase
import com.example.p3project.sources.repository.TaskRepository
import com.example.p3project.sources.repository.TaskRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideTaskDatabase(app: Application): TaskDatabase {
        return Room.databaseBuilder(
            app,
            TaskDatabase::class.java,
            TaskDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideInterruptScheduler(@ApplicationContext context: Context): InterruptScheduler {
        return InterruptSchedulerImpl(context)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(db: TaskDatabase): TaskRepository {
        return TaskRepositoryImpl(db.tasksDao())
    }

}