package com.fsa.to_do_app.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.fsa.to_do_app.data.local.MyDatabase
import com.fsa.to_do_app.data.local.entities.Category
import com.fsa.to_do_app.data.local.entities.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date
import kotlin.time.ExperimentalTime

@RunWith(AndroidJUnit4::class)
@SmallTest
class CategoryDaoTest {
    private lateinit var db: MyDatabase
    private lateinit var categoryDao: CategoryDao
    private lateinit var taskDao: TaskDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MyDatabase::class.java
        ).allowMainThreadQueries().build()
        categoryDao = db.getCategoryDao()
        taskDao = db.getTaskDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
    @Test
    fun upsertCategory() = runTest {
        val category = Category(id = 1, name = "Test Category", colorCode = "EF9A9A")
        categoryDao.upsert(category)
        categoryDao.get().test() {
            val emission = awaitItem()
            assert(emission.contains(category))
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteCategory() = runTest {
        val category = Category(id = 1, name = "Test Category", colorCode = "EF9A9A")
        categoryDao.upsert(category)
        categoryDao.delete(category)
        categoryDao.get().test {
            val emission = awaitItem()
            assert(emission.isEmpty())
        }
    }

    @Test
    fun getWithNumberOfActions_calculatesCorrectly() = runTest {
        val category = Category(id = 1, name = "Test Category", colorCode = "EF9A9A")
        categoryDao.upsert(category)

        val task1 = Task(
            id = 1,
            isDone = false,
            content = "Task1",
            date = Date(1681551685000),//April 15, 2023
            categoryId = category.id,
            timeSet = false,
            notificationWorkId = null
        )
        val task2 = Task(
            id = 2,
            isDone = false,
            content = "Task2",
            date = Date(1684143685000),//May 15, 2023
            categoryId = category.id,
            timeSet = false,
            notificationWorkId = null
        )
        val task3 = Task(
            id = 3,
            isDone = false,
            content = "Task3",
            date = Date(1684143685000),//May 15, 2023
            categoryId = category.id,
            timeSet = false,
            notificationWorkId = null
        )
        taskDao.upsert(task1)
        taskDao.upsert(task2)
        taskDao.upsert(task3)
        val categories = categoryDao.getWithNumberOfActions()

        assert(categories.first().numberOfActions == 3)
    }

    @Test
    fun getWithNumberOfActionsByDate_calculatesCorrectly() = runTest {
        val category = Category(id = 1, name = "Test Category", colorCode = "EF9A9A")
        categoryDao.upsert(category)

        val task1 = Task(
            id = 1,
            isDone = false,
            content = "Task1",
            date = Date(1681551685000),//April 15, 2023
            categoryId = category.id,
            timeSet = false,
            notificationWorkId = null
        )
        val task2 = Task(
            id = 2,
            isDone = false,
            content = "Task2",
            date = Date(1684143685000),//May 15, 2023
            categoryId = category.id,
            timeSet = false,
            notificationWorkId = null
        )
        val task3 = Task(
            id = 3,
            isDone = false,
            content = "Task3",
            date = Date(1684143685000),//May 15, 2023
            categoryId = category.id,
            timeSet = false,
            notificationWorkId = null
        )
        taskDao.upsert(task1)
        taskDao.upsert(task2)
        taskDao.upsert(task3)
        val categories = categoryDao.getWithNumberOfActionsByDate(Date(1684143685000))

        assert(categories.first().numberOfActions == 2)
    }
}