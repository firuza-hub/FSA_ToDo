package com.fsa.to_do_app.domain.model

import java.util.*

data class CreateTaskModel(
    val content: String,
    val date: Date?,
    val category: CategoryModel,
    val timeSet: Boolean = false
) {
    companion object {
        val NULL = CreateTaskModel("", null, CategoryModel.NULL, false)
    }
}
