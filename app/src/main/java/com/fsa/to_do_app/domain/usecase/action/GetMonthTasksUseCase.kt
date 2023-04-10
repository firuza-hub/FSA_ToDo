package com.fsa.to_do_app.domain.usecase.action

import com.fsa.to_do_app.domain.mapper.toActionModel
import com.fsa.to_do_app.domain.model.ActionModel
import com.fsa.to_do_app.domain.repo.ActionRepository

class GetMonthTasksUseCase(private val repository: ActionRepository) {
    suspend operator fun invoke(month:Int, year: Int): List<ActionModel> {
        return repository.getByMonth(month = month, year = year).map { it.toActionModel() }
    }
}