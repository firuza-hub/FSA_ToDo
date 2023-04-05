package com.fsa.to_do_app.domain.repo

import com.fsa.to_do_app.data.local.entities.Action


interface ActionRepository {
    suspend fun get(): List<Action>
    suspend fun updateStatus(id: Int, checked: Boolean)
}