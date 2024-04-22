import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.p3project.domain.model.TaskWithRelations
import com.example.p3project.domain.service.InterruptScheduler
import com.example.p3project.domain.repository.TaskRepository
import com.example.p3project.domain.usecases.UseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class CompleteTaskBroadcastReceiver: BroadcastReceiver() {

        @Inject
        lateinit var taskRepository: TaskRepository
        @Inject
        lateinit var taskScheduler: InterruptScheduler

        @Inject
        lateinit var useCases: UseCases

        @OptIn(DelicateCoroutinesApi::class)
        override fun onReceive(context: Context?, intent: Intent?) {
                CoroutineScope(Dispatchers.IO).launch {
                        val taskId: Int = intent?.getIntExtra("TASK_ID", -1)!!
                        val taskInfo: TaskWithRelations? = taskRepository.getTaskInfo(taskId)

                        if (taskInfo != null) {
                                val task = taskInfo.task
                                useCases.sendNotificationUseCase(taskInfo)

                                useCases.scheduleTaskUseCase(task,
                                        task.nextTaskDay(
                                                LocalDate.now().plusDays(1)
                                        )
                                )
                        }
                }
        }
}