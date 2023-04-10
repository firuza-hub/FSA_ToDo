package com.fsa.to_do_app.data.local.models

import androidx.room.Embedded
import com.fsa.to_do_app.data.local.entities.Task

data class TasksWithCategoryInfo(
    @Embedded val task: Task,
    val categoryColorCode: String
)