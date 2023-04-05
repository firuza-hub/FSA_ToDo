package com.fsa.to_do_app.domain.repo

import com.fsa.to_do_app.data.local.models.ActionWithCategoryInfo


interface ActionRepository {
    suspend fun get(): List<ActionWithCategoryInfo>
    suspend fun updateStatus(id: Int, checked: Boolean)
}