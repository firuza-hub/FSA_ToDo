package com.fsa.to_do_app.data.local.models

import androidx.room.Embedded
import com.fsa.to_do_app.data.local.entities.Category

data class CategoryWithNumberOfActions(
    @Embedded val category: Category,
    val numberOfActions: Int
)
