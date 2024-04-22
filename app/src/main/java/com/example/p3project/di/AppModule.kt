package com.example.p3project.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.p3project.domain.service.InterruptScheduler
import com.example.p3project.presentation.services.InterruptSchedulerService
import com.example.p3project.data.database.TaskDatabase
import com.example.p3project.domain.repository.TaskRepository
import com.example.p3project.data.repository.TaskRepositoryImpl
import com.example.p3project.domain.usecases.tasks.AddTaskUseCase
import com.example.p3project.domain.usecases.tasks.GetTaskUseCase
import com.example.p3project.domain.usecases.tasks.GetTasksUseCase
import com.example.p3project.domain.usecases.notifications.ScheduleTaskUseCase
import com.example.p3project.domain.usecases.notifications.SendNotificationUseCase
import com.example.p3project.domain.usecases.UseCases
import com.example.p3project.domain.usecases.categories.AllCategoriesUseCase
import com.example.p3project.domain.usecases.categories.AssignCategoryUseCase
import com.example.p3project.domain.usecases.categories.CreateCategoryUseCase
import com.example.p3project.domain.usecases.categories.DeleteCategoryUseCase
import com.example.p3project.domain.usecases.categories.UnassignCategoryUseCase
import com.example.p3project.domain.usecases.completions.CompleteTaskUseCase
import com.example.p3project.domain.usecases.completions.UncompleteTaskUseCase
import com.example.p3project.domain.usecases.notifications.ScheduleFollowUpNotificationUseCase
import com.example.p3project.domain.usecases.tasks.DeleteTaskUseCase
import com.example.p3project.domain.usecases.tasks.ModifyTaskUseCase
import com.example.p3project.domain.usecases.tasks.SetTaskEnabledUseCase
import com.example.p3project.presentation.services.NotificationService
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
        return InterruptSchedulerService(context)
    }

    @Provides
    @Singleton
    fun provideNotificationService(@ApplicationContext context: Context): NotificationService {
        return NotificationService(context)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(db: TaskDatabase): TaskRepository {
        return TaskRepositoryImpl(db.tasksDao())
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: TaskRepository, scheduler: InterruptScheduler,
                        notificationService: NotificationService
    ): UseCases {
        return UseCases(
                addTaskUseCase = AddTaskUseCase(repository),
                getTaskByIdUseCase = GetTaskUseCase(repository),
                getTasksUseCase = GetTasksUseCase(repository),

                scheduleTaskUseCase = ScheduleTaskUseCase(scheduler),
                scheduleFollowUpNotificationUseCase = ScheduleFollowUpNotificationUseCase(scheduler),

                sendNotificationUseCase = SendNotificationUseCase(notificationService),

                completeTasksUseCase = CompleteTaskUseCase(repository),
                createCategoryUseCase = CreateCategoryUseCase(repository),
                allCategoriesUseCase = AllCategoriesUseCase(repository),
                deleteCategoryUseCase = DeleteCategoryUseCase(repository),
                assignCategoryUseCase = AssignCategoryUseCase(repository),
                deleteTaskUseCase = DeleteTaskUseCase(repository),
                unassignCategoryUseCase = UnassignCategoryUseCase(repository),
                modifyTaskUseCase = ModifyTaskUseCase(repository),
                uncompleteTasksUseCase = UncompleteTaskUseCase(repository),

                setTaskEnabledUseCase = SetTaskEnabledUseCase(repository)
            )
    }

}