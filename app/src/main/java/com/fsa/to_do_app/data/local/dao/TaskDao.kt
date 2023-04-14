package com.fsa.to_do_app.data.local.dao

import androidx.room.*
import com.fsa.to_do_app.data.local.entities.Task
import com.fsa.to_do_app.data.local.models.TasksWithCategoryInfo
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface TaskDao {
    @Query("SELECT a.*,c.colorCode AS categoryColorCode FROM Tasks a join Categories c on a.categoryId = c.id")
    fun getWithCategoryInfo(): Flow<List<TasksWithCategoryInfo>>
    @Query("UPDATE Tasks SET isDone = :checked where id = :id")
    suspend fun updateStatus(id:Int, checked: Boolean)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(toTask: Task)
    @Query("SELECT a.*,c.colorCode AS categoryColorCode FROM Tasks a join Categories c on a.categoryId = c.id where cast(strftime('%Y',datetime(a.date/1000, 'unixepoch')) as numeric) = :year and cast(strftime('%m',datetime(a.date/1000, 'unixepoch')) as numeric) = :month")
    fun getByMonth(month: Int, year: Int):List<TasksWithCategoryInfo>
    @Delete()
    fun delete(task: Task)
    @Query("SELECT a.*,c.colorCode AS categoryColorCode FROM Tasks a join Categories c on a.categoryId = c.id where date(a.date / 1000,'unixepoch') = date(:date / 1000,'unixepoch')")
    fun getWithCategoryInfoForDate(date: Date): Flow<List<TasksWithCategoryInfo>>
}