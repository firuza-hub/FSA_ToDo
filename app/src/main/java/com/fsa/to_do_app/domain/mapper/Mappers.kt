package com.fsa.to_do_app.domain.mapper

import com.fsa.to_do_app.data.local.entities.Action
import com.fsa.to_do_app.data.local.entities.Category
import com.fsa.to_do_app.data.local.entities.CategoryWithNumberOfActions
import com.fsa.to_do_app.domain.model.ActionModel
import com.fsa.to_do_app.domain.model.CategoryModel


fun CategoryWithNumberOfActions.toCategoryModel(): CategoryModel = CategoryModel(category.id, category.name, category.colorCode, category.dateCreated, numberOfActions?:0)
fun Action.toActionModel(): ActionModel = ActionModel(id, isDone,content, date)