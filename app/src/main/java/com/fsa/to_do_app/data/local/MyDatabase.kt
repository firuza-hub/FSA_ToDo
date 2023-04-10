package com.fsa.to_do_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fsa.to_do_app.data.local.dao.TaskDao
import com.fsa.to_do_app.data.local.dao.CategoryDao
import com.fsa.to_do_app.data.local.entities.Task
import com.fsa.to_do_app.data.local.entities.Category
import com.fsa.to_do_app.util.Converters

@Database(entities = [Task::class, Category::class], version = 1)
@TypeConverters(Converters::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getActionDao(): TaskDao
    abstract fun getCategoryDao(): CategoryDao
}

