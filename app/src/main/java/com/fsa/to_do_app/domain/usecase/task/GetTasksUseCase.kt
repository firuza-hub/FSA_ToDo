package com.fsa.to_do_app.domain.usecase.task

import com.fsa.to_do_app.domain.mapper.toTaskModel
import com.fsa.to_do_app.domain.model.TaskModel
import com.fsa.to_do_app.domain.repo.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTasksUseCase(private val repo: TaskRepository) {
    suspend operator fun invoke(): Flow<List<TaskModel>> {
        return repo.get().map { it.map { t -> t.toTaskModel() }  }
    }
}