package com.fsa.to_do_app.domain.usecase.task

import com.fsa.to_do_app.domain.model.TaskModel
import com.fsa.to_do_app.domain.repo.TaskRepository

class DeleteTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(task:TaskModel) {
        repository.delete(task)
    }
}