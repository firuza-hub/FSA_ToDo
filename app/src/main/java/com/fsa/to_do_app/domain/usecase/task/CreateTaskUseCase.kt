package com.fsa.to_do_app.domain.usecase.task

import android.util.Log
import com.fsa.to_do_app.domain.model.CreateTaskModel
import com.fsa.to_do_app.domain.repo.TaskRepository
import com.fsa.to_do_app.presentation.service.ReminderWorkerService
import java.util.*

class CreateTaskUseCase(
    private val repo: TaskRepository,
    private val reminderWorkerService: ReminderWorkerService
) {
    suspend operator fun invoke(model: CreateTaskModel) {
        try {
            val id = repo.create(model)
            model.date?.let {
                val todayDateTime = Calendar.getInstance()
                val delayInSeconds = (it.time / 1000L) - (todayDateTime.timeInMillis / 1000L)
                val workId = reminderWorkerService.createNotificationWorkRequest(
                    model.content,
                    delayInSeconds
                )

                repo.updateWorkId(workId, id)
            }
        }catch (ex:Exception){
            Log.e("SAVE_TASK", ex.stackTraceToString())
        }
    }
}