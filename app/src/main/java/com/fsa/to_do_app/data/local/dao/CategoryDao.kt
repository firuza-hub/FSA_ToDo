package com.fsa.to_do_app.data.local.dao

import androidx.room.*
import com.fsa.to_do_app.data.local.entities.Category
import com.fsa.to_do_app.data.local.models.CategoryWithNumberOfActions
import java.util.*

@Dao
interface CategoryDao {
    @Query("SELECT *, (select count(*) from Tasks a where a.categoryId = c.id ) as numberOfActions FROM Categories c")
    suspend fun getWithNumberOfActions(): List<CategoryWithNumberOfActions>
    @Query("SELECT *, (select count(*) from Tasks a where a.categoryId = c.id and date(a.date / 1000,'unixepoch') = date(:date / 1000,'unixepoch')) as numberOfActions FROM Categories c")
    fun getWithNumberOfActionsByDate(date: Date): List<CategoryWithNumberOfActions>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(category: Category)
    @Update
    fun update(category: Category)
    @Delete
    fun delete(category: Category)
}