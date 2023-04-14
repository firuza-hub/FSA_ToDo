package com.fsa.to_do_app.domain.usecase.category

import com.fsa.to_do_app.domain.mapper.toCategoryModel
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.domain.repo.CategoryRepository
import java.util.*

class GetCategoriesUseCase(private val repo: CategoryRepository) {
    suspend operator fun invoke(showAll: Boolean = true): List<CategoryModel> {
        return if (showAll) repo.get().map { it.toCategoryModel() }
        else repo.getByDate(date = Date()).map { it.toCategoryModel() }
    }
}