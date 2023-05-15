package com.fsa.to_do_app.data.local

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fsa.to_do_app.data.local.dao.CategoryDao
import com.fsa.to_do_app.data.local.dao.TaskDao
import com.fsa.to_do_app.data.local.entities.Category
import com.fsa.to_do_app.data.local.entities.Task
import com.fsa.to_do_app.util.Converters


@Database(entities = [Task::class, Category::class], version = 2)
@TypeConverters(Converters::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
    abstract fun getCategoryDao(): CategoryDao
}

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE 'Tasks' ADD COLUMN 'timeSet' INTEGER NOT NULL DEFAULT 0")
        Log.i("DB_ MIGRATION", "Migration 1->2 executed")
    }
}

