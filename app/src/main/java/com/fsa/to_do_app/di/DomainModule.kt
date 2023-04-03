package com.fsa.to_do_app.di

import com.fsa.to_do_app.domain.usecase.action.*
import com.fsa.to_do_app.domain.usecase.category.*
import org.koin.dsl.module

val domainModule = module {

    //ACTION
    factory { GetActionsUseCase(get()) }
    factory { CreateActionUseCase(get()) }

    //CATEGORY
    factory { GetCategoriesUseCase(get()) }
    factory { CreateCategoryUseCase(get()) }
}