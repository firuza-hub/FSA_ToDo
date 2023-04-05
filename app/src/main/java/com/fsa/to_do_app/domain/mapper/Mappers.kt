package com.fsa.to_do_app.domain.mapper

import com.fsa.to_do_app.data.local.models.ActionWithCategoryInfo
import com.fsa.to_do_app.data.local.models.CategoryWithNumberOfActions
import com.fsa.to_do_app.domain.model.ActionModel
import com.fsa.to_do_app.domain.model.CategoryModel


fun CategoryWithNumberOfActions.toCategoryModel(): CategoryModel = CategoryModel(category.id, category.name, category.colorCode, category.dateCreated, numberOfActions?:0)
fun ActionWithCategoryInfo.toActionModel(): ActionModel = ActionModel(action.id, action.isDone,action.content, action.date,action.categoryId, categoryColorCode)