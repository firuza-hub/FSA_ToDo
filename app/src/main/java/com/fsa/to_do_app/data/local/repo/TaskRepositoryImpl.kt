package com.fsa.to_do_app.data.local.repo

import com.fsa.to_do_app.data.local.dao.TaskDao
import com.fsa.to_do_app.data.local.models.TasksWithCategoryInfo
import com.fsa.to_do_app.domain.mapper.toTask
import com.fsa.to_do_app.domain.model.CreateTaskModel
import com.fsa.to_do_app.domain.model.TaskModel
import com.fsa.to_do_app.domain.repo.TaskRepository
import kotlinx.coroutines.flow.Flow
import java.util.*

class TaskRepositoryImpl(private val dao: TaskDao): TaskRepository {
    override suspend fun get(): Flow<List<TasksWithCategoryInfo>> {
        return dao.getWithCategoryInfo()
    }

    override suspend fun updateStatus(id: Int, checked: Boolean) {
        dao.updateStatus(id, checked)
    }

    override suspend fun create(model: CreateTaskModel) {
        dao.create(model.toTask())
    }

    override suspend fun getByMonth(month: Int, year: Int): List<TasksWithCategoryInfo> {
        return dao.getByMonth(month = month, year = year)
    }

    override suspend fun delete(task: TaskModel) {
        dao.delete(task.toTask())
    }

    override fun getForDate(date: Date): Flow<List<TasksWithCategoryInfo>> {
        return dao.getWithCategoryInfoForDate(date)
    }
}