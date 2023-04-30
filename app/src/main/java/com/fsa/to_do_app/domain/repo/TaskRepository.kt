package com.fsa.to_do_app.domain.repo

import com.fsa.to_do_app.data.local.models.TasksWithCategoryInfo
import com.fsa.to_do_app.domain.model.CreateTaskModel
import com.fsa.to_do_app.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow
import java.util.*


interface TaskRepository {
    suspend fun get(): Flow<List<TasksWithCategoryInfo>>
    suspend fun updateStatus(id: Int, checked: Boolean)
    suspend fun create(model: CreateTaskModel): Long
    suspend fun update(model: TaskModel): Long
    suspend fun getByMonth(month: Int, year: Int): List<TasksWithCategoryInfo>
    suspend fun delete(task: TaskModel)
    fun getForDate(date: Date): Flow<List<TasksWithCategoryInfo>>
    suspend fun getById(id: Int): TasksWithCategoryInfo
    fun updateWorkId(workId: UUID, taskId: Long)
    suspend fun getWorkId(id: Long): UUID?
}