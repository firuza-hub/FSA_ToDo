package com.fsa.to_do_app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.fsa.to_do_app.data.local.entities.Action

@Dao
interface ActionDao {
    @Query("SELECT * FROM Actions")
    suspend fun get(): List<Action>//TODO: Add new model and query for Actions with Categ color code fetching
    @Query("UPDATE Actions SET isDone = :checked where id = :id")
    suspend fun updateStatus(id:Int, checked: Boolean)
}