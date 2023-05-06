package com.fsa.to_do_app.domain.model

import java.util.*

data class TaskModel(
    val id: Int,
    val isDone: Boolean,
    val content: String,
    val date: Date?,
    val category:CategoryModel,
    val notificationWorkId: String?,
    val timeSet:Boolean = false
){
    companion object{
        val NULL = TaskModel(-1, false,"",null, CategoryModel.NULL, null, false)
    }
}