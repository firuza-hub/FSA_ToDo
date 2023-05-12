package com.fsa.to_do_app.domain.usecase.task

import com.fsa.to_do_app.domain.repo.TaskRepository

class UpdateTaskStatusUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(id: Int, checked: Boolean) {
        repository.updateStatus(id, checked)
    }
}