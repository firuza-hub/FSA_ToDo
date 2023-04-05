package com.fsa.to_do_app.data.local.repo

import com.fsa.to_do_app.data.local.dao.CategoryDao
import com.fsa.to_do_app.data.local.models.CategoryWithNumberOfActions
import com.fsa.to_do_app.domain.repo.CategoryRepository

class CategoryRepositoryImpl(private val dao: CategoryDao): CategoryRepository {
    override suspend fun get(): List<CategoryWithNumberOfActions> {
        return dao.getWithNumberOfActions()
    }

}