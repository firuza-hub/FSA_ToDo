package com.fsa.to_do_app.domain.usecase.category

import com.fsa.to_do_app.data.local.entities.Category
import com.fsa.to_do_app.domain.repo.CategoryRepository

class GetCategoriesUseCase(private val repo: CategoryRepository) {
    suspend operator fun invoke(): List<Category> {
        return repo.get()
    }
}