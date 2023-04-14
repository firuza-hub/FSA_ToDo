package com.fsa.to_do_app.domain.repo

import com.fsa.to_do_app.data.local.models.CategoryWithNumberOfActions
import java.util.*

interface CategoryRepository {
    suspend fun get(): List<CategoryWithNumberOfActions>
    suspend fun getByDate(date: Date): List<CategoryWithNumberOfActions>
}