package com.fsa.to_do_app.domain.repo

import com.fsa.to_do_app.data.local.models.CategoryWithNumberOfActions

interface CategoryRepository {
    suspend fun get(): List<CategoryWithNumberOfActions>
}