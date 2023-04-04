package com.fsa.to_do_app.domain.usecase.category

import com.fsa.to_do_app.domain.mapper.toCategoryModel
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.domain.repo.CategoryRepository

class GetCategoriesUseCase(private val repo: CategoryRepository) {
    suspend operator fun invoke(): List<CategoryModel> {
        return repo.get().map { it.toCategoryModel() }
    }
}