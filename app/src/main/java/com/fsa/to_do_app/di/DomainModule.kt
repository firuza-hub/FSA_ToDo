package com.fsa.to_do_app.di

import com.fsa.to_do_app.domain.usecase.task.*
import com.fsa.to_do_app.domain.usecase.category.*
import org.koin.dsl.module

val domainModule = module {

    //ACTION
    factory { GetTasksUseCase(get()) }
    factory { CreateTaskUseCase(get(), get()) }
    factory { UpdateTaskStatusUseCase(get()) }
    factory { GetMonthTasksUseCase(get()) }
    factory { DeleteTaskUseCase(get()) }
    factory { EditTaskUseCase(get(), get()) }
    factory { GetTaskUseCase(get()) }

    //CATEGORY
    factory { GetCategoriesUseCase(get()) }
    factory { CreateCategoryUseCase(get()) }
}