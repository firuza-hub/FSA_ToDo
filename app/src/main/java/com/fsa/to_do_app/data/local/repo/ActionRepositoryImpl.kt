package com.fsa.to_do_app.data.local.repo

import com.fsa.to_do_app.data.local.dao.ActionDao
import com.fsa.to_do_app.data.local.models.ActionWithCategoryInfo
import com.fsa.to_do_app.domain.repo.ActionRepository

class ActionRepositoryImpl(private val dao: ActionDao): ActionRepository {
    override suspend fun get(): List<ActionWithCategoryInfo> {
        return dao.getWithCategoryInfo()
    }

    override suspend fun updateStatus(id: Int, checked: Boolean) {
        dao.updateStatus(id, checked)
    }
}