package com.fsa.to_do_app.domain.usecase.action

import com.fsa.to_do_app.data.local.entities.Action
import com.fsa.to_do_app.domain.repo.ActionRepository

class GetActionsUseCase(private val repo: ActionRepository) {
    suspend operator fun invoke(): List<Action> {
        return repo.get()
    }
}