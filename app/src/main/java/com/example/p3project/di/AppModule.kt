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
import com.example.p3project.sources.usecases.AddTaskUseCase
import com.example.p3project.sources.usecases.GetTaskByIdUseCase
import com.example.p3project.sources.usecases.GetTasksUseCase
import com.example.p3project.sources.usecases.ScheduleTaskUseCase
import com.example.p3project.sources.usecases.SendNotificationUseCase
import com.example.p3project.sources.usecases.UseCases
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

    @Provides
    @Singleton
    fun provideUseCases(repository: TaskRepository, scheduler: InterruptScheduler): UseCases {
        return UseCases(
                addTaskUseCase = AddTaskUseCase(repository),
                getTaskByIdUseCase = GetTaskByIdUseCase(repository),
                getTasksUseCase = GetTasksUseCase(repository),
                scheduleTaskUseCase = ScheduleTaskUseCase(scheduler),
                sendNotificationUseCase = SendNotificationUseCase()
              )
    }

}