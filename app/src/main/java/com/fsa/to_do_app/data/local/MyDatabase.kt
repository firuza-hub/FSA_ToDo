package com.fsa.to_do_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fsa.to_do_app.data.local.dao.ActionDao
import com.fsa.to_do_app.data.local.dao.CategoryDao
import com.fsa.to_do_app.data.local.entities.Action
import com.fsa.to_do_app.data.local.entities.Category
import com.fsa.to_do_app.util.Converters

@Database(entities = [Action::class, Category::class], version = 1)
@TypeConverters(Converters::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getActionDao(): ActionDao
    abstract fun getCategoryDao(): CategoryDao
}

