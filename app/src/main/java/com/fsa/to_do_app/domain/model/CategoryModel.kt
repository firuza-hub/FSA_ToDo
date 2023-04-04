package com.fsa.to_do_app.domain.model

import java.util.*

data class CategoryModel(
    val id: Int,
    val name: String,
    val colorCode: String,
    val dateCreated: Date,
    val numberOfTasks: Int
){
    companion object{
        val NULL = CategoryModel(0, "", "FFFFFF", Date(), 0)
    }
}