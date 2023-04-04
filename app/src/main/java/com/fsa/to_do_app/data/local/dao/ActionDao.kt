package com.fsa.to_do_app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.fsa.to_do_app.data.local.entities.Action

@Dao
interface ActionDao {
    @Query("SELECT * FROM Actions")
    suspend fun get(): List<Action>
}