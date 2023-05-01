package com.fsa.to_do_app.domain.usecase.category

import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.domain.repo.CategoryRepository

class UpdateCategoryUseCase (private val repo: CategoryRepository) {
    suspend operator fun invoke(category: CategoryModel){
        repo.update(category)
    }
}