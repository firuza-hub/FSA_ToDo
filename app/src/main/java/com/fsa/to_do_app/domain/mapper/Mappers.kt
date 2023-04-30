package com.fsa.to_do_app.domain.mapper

import com.fsa.to_do_app.data.local.entities.Category
import com.fsa.to_do_app.data.local.entities.Task
import com.fsa.to_do_app.data.local.models.TasksWithCategoryInfo
import com.fsa.to_do_app.data.local.models.CategoryWithNumberOfActions
import com.fsa.to_do_app.domain.model.TaskModel
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.domain.model.CreateCategoryModel
import com.fsa.to_do_app.domain.model.CreateTaskModel
import com.fsa.to_do_app.presentation.common.toHexString
import java.util.*


fun CategoryWithNumberOfActions.toCategoryModel(): CategoryModel =
    CategoryModel(
        category.id,
        category.name,
        category.colorCode,
        category.dateCreated,
        numberOfActions ?: 0
    )

fun TasksWithCategoryInfo.toTaskModel(): TaskModel = TaskModel(
    task.id,
    task.isDone,
    task.content,
    task.date,
    category = CategoryModel(
        id = task.categoryId,
        colorCode = categoryColorCode,
        name = categoryName,
        dateCreated = Date(),
        numberOfActions = -1
    )
)

fun CreateTaskModel.toTask(): Task = Task(
    isDone = false, content = content, date = date, categoryId = category.id, workId = null
)


fun TaskModel.toTask(): Task = Task(
    isDone = false, content = content, date = date, categoryId = category.id, id = id, workId = null
)


fun CreateCategoryModel.toCategory(): Category = Category(
    name = name, colorCode = color.toHexString()
)