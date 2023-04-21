package com.fsa.to_do_app.domain.usecase.category

import androidx.compose.ui.graphics.Color
import com.fsa.to_do_app.domain.model.CreateCategoryModel
import com.fsa.to_do_app.domain.repo.CategoryRepository

class CreateCategoryUseCase(private val repo: CategoryRepository) {
    suspend operator fun invoke(name: String, color: Color){
        repo.create(CreateCategoryModel(name = name, color = color))
    }

}