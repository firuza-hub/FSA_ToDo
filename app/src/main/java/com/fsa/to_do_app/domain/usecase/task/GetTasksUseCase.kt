package com.fsa.to_do_app.domain.usecase.task

import com.fsa.to_do_app.domain.mapper.toTaskModel
import com.fsa.to_do_app.domain.model.TaskModel
import com.fsa.to_do_app.domain.repo.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class GetTasksUseCase(private val repo: TaskRepository) {
    suspend operator fun invoke(showAll: Boolean): Flow<List<TaskModel>> {
        return if(showAll)
         repo.get().map { it.map { t -> t.toTaskModel() }  }
        else
            repo.getForDate(Date()).map { it.map { t -> t.toTaskModel() }  }
    }
}