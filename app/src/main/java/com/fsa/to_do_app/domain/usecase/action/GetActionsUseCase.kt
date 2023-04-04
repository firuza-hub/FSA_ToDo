package com.fsa.to_do_app.domain.usecase.action

import com.fsa.to_do_app.domain.mapper.toActionModel
import com.fsa.to_do_app.domain.model.ActionModel
import com.fsa.to_do_app.domain.repo.ActionRepository

class GetActionsUseCase(private val repo: ActionRepository) {
    suspend operator fun invoke(): List<ActionModel> {
        return repo.get().map { it.toActionModel() }
    }
}