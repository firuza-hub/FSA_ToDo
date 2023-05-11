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
            val prevId = repo.getWorkId(id)
            prevId?.let { prevWorkId ->
                reminderWorkerService.cancelNotificationWorkRequest(prevWorkId)
            }

            val todayDateTime = Calendar.getInstance()
            var workId: UUID? = null

            if (it.after(todayDateTime.time)) {
                val delayInSeconds = (it.time / 1000L) - (todayDateTime.timeInMillis / 1000L)
                workId = reminderWorkerService.createNotificationWorkRequest(
                    model.content,
                    delayInSeconds
                )
            }
            repo.updateWorkId(workId, id)
        }
    }
}