package com.fsa.to_do_app.domain.repo

import com.fsa.to_do_app.data.local.models.TasksWithCategoryInfo
import com.fsa.to_do_app.domain.model.CreateTaskModel
import com.fsa.to_do_app.domain.model.TaskModel


interface TaskRepository {
    suspend fun get(): List<TasksWithCategoryInfo>
    suspend fun updateStatus(id: Int, checked: Boolean)
    suspend fun create(model: CreateTaskModel)
    suspend fun getByMonth(month: Int, year: Int): List<TasksWithCategoryInfo>
    suspend fun delete(task: TaskModel)
}