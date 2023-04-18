package com.fsa.to_do_app.domain.usecase.task

import com.fsa.to_do_app.domain.mapper.toTaskModel
import com.fsa.to_do_app.domain.model.TaskModel
import com.fsa.to_do_app.domain.repo.TaskRepository

class GetTaskUseCase(private val repo: TaskRepository) {
    suspend operator fun invoke(id: Int): TaskModel {
        return repo.getById(id).toTaskModel()
    }
}