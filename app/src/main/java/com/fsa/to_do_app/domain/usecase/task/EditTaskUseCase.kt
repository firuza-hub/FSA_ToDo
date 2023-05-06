package com.fsa.to_do_app.domain.usecase.task

import com.fsa.to_do_app.domain.model.TaskModel
import com.fsa.to_do_app.domain.repo.TaskRepository
import com.fsa.to_do_app.presentation.service.ReminderWorkerService
import java.util.*

class EditTaskUseCase(
    private val repo: TaskRepository,
    private val reminderWorkerService: ReminderWorkerService,
) {
    suspend operator fun invoke(model: TaskModel) {
        val id = repo.update(model)

        if (!model.timeSet) return
        model.date?.let {
            val todayDateTime = Calendar.getInstance()
            val delayInSeconds = (it.time / 1000L) - (todayDateTime.timeInMillis / 1000L)
            val workId = reminderWorkerService.createNotificationWorkRequest(
                model.content,
                delayInSeconds
            )
            val prevId = repo.getWorkId(id)
            prevId?.let { prevWorkId ->
                reminderWorkerService.cancelNotificationWorkRequest(prevWorkId)
            }
            repo.updateWorkId(workId, id)
        }
    }
}