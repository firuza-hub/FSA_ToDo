package com.fsa.to_do_app.data.local.repo

import com.fsa.to_do_app.data.local.dao.CategoryDao
import com.fsa.to_do_app.data.local.models.CategoryWithNumberOfActions
import com.fsa.to_do_app.domain.mapper.toCategory
import com.fsa.to_do_app.domain.model.CreateCategoryModel
import com.fsa.to_do_app.domain.repo.CategoryRepository
import java.util.*

class CategoryRepositoryImpl(private val dao: CategoryDao): CategoryRepository {
    override suspend fun get(): List<CategoryWithNumberOfActions> {
        return dao.getWithNumberOfActions()
    }

    override suspend fun getByDate(date: Date): List<CategoryWithNumberOfActions> {
        return dao.getWithNumberOfActionsByDate(date)
    }

    override suspend fun create(createCategoryModel: CreateCategoryModel) {
        dao.create(createCategoryModel.toCategory())
    }

}