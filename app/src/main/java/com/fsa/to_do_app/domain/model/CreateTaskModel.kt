package com.fsa.to_do_app.domain.model

import java.util.*

data class CreateTaskModel(
    val content: String,
    val date: Date?,
    val category: CategoryModel
){
    companion object{
        val NULL = CreateTaskModel("", null,CategoryModel.NULL)
    }
}
