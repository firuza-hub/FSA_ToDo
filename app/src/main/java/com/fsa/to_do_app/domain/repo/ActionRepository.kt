package com.fsa.to_do_app.domain.repo

import com.fsa.to_do_app.data.local.models.ActionWithCategoryInfo
import com.fsa.to_do_app.domain.model.CreateActionModel


interface ActionRepository {
    suspend fun get(): List<ActionWithCategoryInfo>
    suspend fun updateStatus(id: Int, checked: Boolean)
    suspend fun create(model: CreateActionModel)
    suspend fun getByMonth(month: Int, year: Int): List<ActionWithCategoryInfo>
}