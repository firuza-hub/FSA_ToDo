package com.fsa.to_do_app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fsa.to_do_app.data.local.entities.Action
import com.fsa.to_do_app.data.local.models.ActionWithCategoryInfo

@Dao
interface ActionDao {
    @Query("SELECT a.*,c.colorCode AS categoryColorCode FROM Actions a join Categories c on a.categoryId = c.id")
    suspend fun getWithCategoryInfo(): List<ActionWithCategoryInfo>//TODO: Add new model and query for Actions with Categ color code fetching
    @Query("UPDATE Actions SET isDone = :checked where id = :id")
    suspend fun updateStatus(id:Int, checked: Boolean)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(toAction: Action)
}