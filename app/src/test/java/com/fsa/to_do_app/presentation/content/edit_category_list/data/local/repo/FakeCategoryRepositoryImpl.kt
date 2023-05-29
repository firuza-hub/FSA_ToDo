package com.fsa.to_do_app.presentation.content.edit_category_list.data.local.repo

import com.fsa.to_do_app.data.local.entities.Category
import com.fsa.to_do_app.data.local.entities.Task
import com.fsa.to_do_app.data.local.models.CategoryWithNumberOfActions
import com.fsa.to_do_app.domain.mapper.toCategory
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.domain.model.CreateCategoryModel
import com.fsa.to_do_app.domain.repo.CategoryRepository
import java.util.Date

class FakeCategoryRepositoryImpl : CategoryRepository {
    private val categories = mutableListOf<Category>()
    private val tasks = mutableListOf<Task>()
    override suspend fun get(): List<CategoryWithNumberOfActions> {
        return categories.map { category ->
            CategoryWithNumberOfActions(
                category,
                tasks.count { it.categoryId == category.id })
        }.toList()
    }

    override suspend fun getByDate(date: Date): List<CategoryWithNumberOfActions> {
        return categories.filter { it.dateCreated == date }.map { category ->
            CategoryWithNumberOfActions(
                category,
                tasks.count { it.categoryId == category.id })
        }
    }

    override suspend fun create(createCategoryModel: CreateCategoryModel) {
        categories.add(createCategoryModel.toCategory())
    }

    override suspend fun update(categoryModel: CategoryModel) {
        val indexToUpdate = categories.indexOf(categoryModel.toCategory())
        categories[indexToUpdate] = categoryModel.toCategory()
    }

    override suspend fun delete(category: Category) {
        categories.remove(category)
    }
}