package com.fsa.to_do_app.domain.repo

import com.fsa.to_do_app.data.local.entities.Category
import com.fsa.to_do_app.data.local.models.CategoryWithNumberOfActions
import com.fsa.to_do_app.domain.model.CreateCategoryModel
import java.util.*

interface CategoryRepository {
    suspend fun get(): List<CategoryWithNumberOfActions>
    suspend fun getByDate(date: Date): List<CategoryWithNumberOfActions>
    suspend fun create(createCategoryModel: CreateCategoryModel)
    suspend fun delete(category: Category)
}