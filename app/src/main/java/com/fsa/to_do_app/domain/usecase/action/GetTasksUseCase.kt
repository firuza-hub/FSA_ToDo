package com.fsa.to_do_app.domain.usecase.action

import com.fsa.to_do_app.domain.mapper.toTaskModel
import com.fsa.to_do_app.domain.model.TaskModel
import com.fsa.to_do_app.domain.repo.TaskRepository

class GetTasksUseCase(private val repo: TaskRepository) {
    suspend operator fun invoke(): List<TaskModel> {
        return repo.get().map { it.toTaskModel() }
    }
}