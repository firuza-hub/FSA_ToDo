package com.fsa.to_do_app.domain.repo

import com.fsa.to_do_app.data.local.entities.Category


interface CategoryRepository {
    suspend fun get(): List<Category>
}