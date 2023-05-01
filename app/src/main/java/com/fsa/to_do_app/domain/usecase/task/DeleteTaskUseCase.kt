package com.fsa.to_do_app.domain.usecase.task

import com.fsa.to_do_app.domain.model.TaskModel
import com.fsa.to_do_app.domain.repo.TaskRepository
import com.fsa.to_do_app.presentation.service.ReminderWorkerService
import java.util.*

class DeleteTaskUseCase(
    private val repository: TaskRepository,
    private val workerService: ReminderWorkerService
) {
    suspend operator fun invoke(task: TaskModel) {
        if (!task.notificationWorkId.isNullOrEmpty()) {
            workerService.cancelNotificationWorkRequest(UUID.fromString(task.notificationWorkId))
        }
        repository.delete(task)
    }
}