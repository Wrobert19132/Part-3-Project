package com.example.p3project.domain.usecases.tasks

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.p3project.DBTest
import com.example.p3project.data.database.TaskDatabase
import com.example.p3project.data.repository.TaskRepositoryImpl
import com.example.p3project.domain.model.Task
import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.domain.repository.TaskRepository
import com.example.p3project.domain.usecases.tasks.ModifyTaskUseCase
import com.example.p3project.domain.util.InvalidTaskException
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class SetTaskEnabledTests: DBTest() {
    lateinit var task: Task

    @Before()
    fun setupTask() = runTest {
        val newTask = Task("Test", "Test",
            LocalTime.of(1, 30), LocalDate.of(2023, 1, 1),0, 1
        )
        repo.addTask(newTask)
        task = repo.getTaskInfo(newTask.taskId)!!.task
    }


    @Test()
    fun setTaskEnabled_setEnabled() = runTest {
        val setTaskEnabledUseCase = SetTaskEnabledUseCase(repo)

        setTaskEnabledUseCase(task, true)

        assertEquals(true, repo.getTaskInfo(task.taskId)!!.task.enabled)
        setTaskEnabledUseCase(task, false)

        assertEquals(false, repo.getTaskInfo(task.taskId)!!.task.enabled)

    }


}