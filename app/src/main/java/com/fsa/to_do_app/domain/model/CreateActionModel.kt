package com.fsa.to_do_app.domain.model

import java.util.*

data class CreateActionModel(
    val content: String,
    val date: Date?,
    val category: CategoryModel
){
    companion object{
        val NULL = CreateActionModel("", null,CategoryModel.NULL)
    }
}
