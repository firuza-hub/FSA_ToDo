package com.fsa.to_do_app.di

import androidx.room.Room
import com.fsa.to_do_app.data.local.MIGRATION_1_2
import com.fsa.to_do_app.data.local.MyDatabase
import com.fsa.to_do_app.data.local.repo.TaskRepositoryImpl
import com.fsa.to_do_app.data.local.repo.CategoryRepositoryImpl
import com.fsa.to_do_app.domain.repo.TaskRepository
import com.fsa.to_do_app.domain.repo.CategoryRepository
import org.koin.dsl.module

val dataModule = module {
    //DB and DAO
    single {
        Room.databaseBuilder(get(), MyDatabase::class.java, "TODO_FSA_DB")
            .createFromAsset("database/ToDo.db").addMigrations(MIGRATION_1_2).build()
    }
    single { get<MyDatabase>().getTaskDao() }
    single { get<MyDatabase>().getCategoryDao() }

    //REPO
    single<TaskRepository> { TaskRepositoryImpl(get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
}