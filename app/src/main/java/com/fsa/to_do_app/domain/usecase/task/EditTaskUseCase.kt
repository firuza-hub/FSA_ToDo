package com.fsa.to_do_app.domain.usecase.task

import com.fsa.to_do_app.domain.model.TaskModel
import com.fsa.to_do_app.domain.repo.TaskRepository

class EditTaskUseCase (private val repo: TaskRepository) {
    suspend operator fun invoke(model: TaskModel) {
        repo.update(model)
    }
}