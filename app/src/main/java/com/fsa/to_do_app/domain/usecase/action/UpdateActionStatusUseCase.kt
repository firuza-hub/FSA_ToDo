package com.fsa.to_do_app.domain.usecase.action

import com.fsa.to_do_app.domain.repo.ActionRepository

class UpdateActionStatusUseCase(private val repository: ActionRepository) {

    suspend operator fun invoke(id: Int, checked: Boolean) {
        repository.updateStatus(id, checked)
    }
}