package com.fsa.to_do_app.data.local.repo

import com.fsa.to_do_app.data.local.dao.ActionDao
import com.fsa.to_do_app.data.local.models.ActionWithCategoryInfo
import com.fsa.to_do_app.domain.mapper.toAction
import com.fsa.to_do_app.domain.model.CreateActionModel
import com.fsa.to_do_app.domain.repo.ActionRepository

class ActionRepositoryImpl(private val dao: ActionDao): ActionRepository {
    override suspend fun get(): List<ActionWithCategoryInfo> {
        return dao.getWithCategoryInfo()
    }

    override suspend fun updateStatus(id: Int, checked: Boolean) {
        dao.updateStatus(id, checked)
    }

    override suspend fun create(model: CreateActionModel) {
        dao.create(model.toAction())
    }

    override suspend fun getByMonth(month: Int, year: Int): List<ActionWithCategoryInfo> {
        return dao.getByMonth(month = month, year = year)
    }
}