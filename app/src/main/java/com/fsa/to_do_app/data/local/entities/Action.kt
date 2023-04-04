package com.fsa.to_do_app.data.local.entities

import androidx.room.*
import java.util.*

@Entity(
    tableName = "Actions", foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Action(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val isDone: Boolean,
    val content: String,
    val date: Date?,
    @ColumnInfo(index = true)
    val categoryId: Int
)