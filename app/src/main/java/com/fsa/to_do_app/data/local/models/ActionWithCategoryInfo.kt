package com.fsa.to_do_app.data.local.models

import androidx.room.Embedded
import com.fsa.to_do_app.data.local.entities.Action

data class ActionWithCategoryInfo(
    @Embedded val action: Action,
    val categoryColorCode: String
)