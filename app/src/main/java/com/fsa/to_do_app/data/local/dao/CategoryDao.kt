package com.fsa.to_do_app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.fsa.to_do_app.data.local.models.CategoryWithNumberOfActions

@Dao
interface CategoryDao {
    @Query("SELECT *, (select count(*) from Actions a where a.categoryId = c.id ) as numberOfActions FROM Categories c")
    suspend fun getWithNumberOfActions(): List<CategoryWithNumberOfActions>
}