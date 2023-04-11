package com.fsa.to_do_app.domain.usecase.task

import com.fsa.to_do_app.domain.mapper.toTaskModel
import com.fsa.to_do_app.domain.model.TaskModel
import com.fsa.to_do_app.domain.repo.TaskRepository

class GetMonthTasksUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(month:Int, year: Int): List<TaskModel> {
        return repository.getByMonth(month = month, year = year).map { it.toTaskModel() }
    }
}