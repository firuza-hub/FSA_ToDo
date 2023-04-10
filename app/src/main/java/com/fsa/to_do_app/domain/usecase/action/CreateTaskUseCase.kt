package com.fsa.to_do_app.domain.usecase.action

import com.fsa.to_do_app.domain.model.CreateTaskModel
import com.fsa.to_do_app.domain.repo.TaskRepository

class CreateTaskUseCase(private val repo: TaskRepository) {
    suspend operator fun invoke(model: CreateTaskModel) {
        repo.create(model)
    }
}