package com.fsa.to_do_app.di

import com.fsa.to_do_app.presentation.content.dashboard.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        DashboardViewModel(
            getCategoriesUseCase = get(),
            getActionsUseCase = get(),
            updateActionStatusUseCase = get()
        )
    }
}