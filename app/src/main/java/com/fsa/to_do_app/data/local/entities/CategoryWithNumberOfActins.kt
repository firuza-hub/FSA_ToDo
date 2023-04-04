package com.fsa.to_do_app.data.local.entities

import androidx.room.Embedded

data class CategoryWithNumberOfActions(
    @Embedded val category: Category,
    val numberOfActions: Int
)
