package com.example.p3project.di

import android.app.Application
import androidx.room.Room
import com.example.p3project.sources.data.TaskDatabase
import com.example.p3project.sources.repository.TaskRepository
import com.example.p3project.sources.repository.TaskRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): TaskDatabase {
        return Room.databaseBuilder(
            app,
            TaskDatabase::class.java,
            TaskDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: TaskDatabase): TaskRepository {
        return TaskRepositoryImpl(db.tasksDao())
    }

}