package com.fsa.to_do_app.domain.usecase.action

import com.fsa.to_do_app.domain.model.CreateActionModel
import com.fsa.to_do_app.domain.repo.ActionRepository

class CreateTaskUseCase(private val repo: ActionRepository) {
    suspend operator fun invoke(model: CreateActionModel) {
        repo.create(model)
    }
}