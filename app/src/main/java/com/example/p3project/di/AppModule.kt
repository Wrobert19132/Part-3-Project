package com.example.p3project.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.p3project.domain.repository.InterruptScheduler
import com.example.p3project.data.repository.InterruptSchedulerImpl
import com.example.p3project.data.database.TaskDatabase
import com.example.p3project.domain.repository.TaskRepository
import com.example.p3project.data.repository.TaskRepositoryImpl
import com.example.p3project.domain.usecases.tasks.AddTaskUseCase
import com.example.p3project.domain.usecases.tasks.GetTaskInfoUseCase
import com.example.p3project.domain.usecases.tasks.AllTaskInfoUseCase
import com.example.p3project.domain.usecases.notifications.ScheduleTaskUseCase
import com.example.p3project.domain.usecases.notifications.SendNotificationUseCase
import com.example.p3project.domain.usecases.UseCases
import com.example.p3project.domain.usecases.categories.AllCategoriesUseCase
import com.example.p3project.domain.usecases.categories.AssignCategoryUseCase
import com.example.p3project.domain.usecases.categories.CreateCategoryUseCase
import com.example.p3project.domain.usecases.categories.DeleteCategoryUseCase
import com.example.p3project.domain.usecases.completions.CompleteTaskUseCase
import com.example.p3project.domain.usecases.tasks.DeleteTaskUseCase
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
        )
            .build()
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
                getTaskByIdUseCase = GetTaskInfoUseCase(repository),
                getTasksUseCase = AllTaskInfoUseCase(repository),
                scheduleTaskUseCase = ScheduleTaskUseCase(scheduler),
                sendNotificationUseCase = SendNotificationUseCase(),
                completeTasksUseCase = CompleteTaskUseCase(repository),
                createCategoryUseCase = CreateCategoryUseCase(repository),
                allCategoriesUseCase = AllCategoriesUseCase(repository),
                deleteCategoryUseCase = DeleteCategoryUseCase(repository),
                assignCategoryUseCase = AssignCategoryUseCase(repository),
                deleteTaskUseCase = DeleteTaskUseCase(repository)
              )
    }

}