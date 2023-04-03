package com.fsa.to_do_app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.fsa.to_do_app.data.local.entities.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM Categories")
    suspend fun get(): List<Category>
}