package com.fsa.to_do_app.data.local.entities

import androidx.room.*
import java.util.*

@Entity(
    tableName = "Tasks", foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val isDone: Boolean,
    val content: String,
    val date: Date?,
    @ColumnInfo(index = true)
    val categoryId: Int
)