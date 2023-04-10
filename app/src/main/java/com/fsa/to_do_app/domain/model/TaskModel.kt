package com.fsa.to_do_app.domain.model

import java.util.*

data class TaskModel(
    val id: Int,
    val isDone: Boolean,
    val content: String,
    val date: Date?,
    val categoryId: Int,
    val categoryColorCode:String
)