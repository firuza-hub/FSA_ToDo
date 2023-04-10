package com.fsa.to_do_app.domain.mapper

import com.fsa.to_do_app.data.local.entities.Task
import com.fsa.to_do_app.data.local.models.TasksWithCategoryInfo
import com.fsa.to_do_app.data.local.models.CategoryWithNumberOfActions
import com.fsa.to_do_app.domain.model.TaskModel
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.domain.model.CreateTaskModel


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
    task.categoryId,
    categoryColorCode
)

fun CreateTaskModel.toTask(): Task = Task(
    isDone = false, content = content, date = date, categoryId = category.id
)


fun TaskModel.toTask(): Task = Task(
    isDone = false, content = content, date = date, categoryId = categoryId, id = id
)