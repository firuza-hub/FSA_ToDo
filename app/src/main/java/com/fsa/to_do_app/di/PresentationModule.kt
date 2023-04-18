package com.fsa.to_do_app.di

import com.fsa.to_do_app.domain.usecase.task.GetTaskUseCase
import com.fsa.to_do_app.presentation.content.create_action.CreateTaskViewModel
import com.fsa.to_do_app.presentation.content.dashboard.DashboardViewModel
import com.fsa.to_do_app.presentation.content.edit_action.EditTaskViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        DashboardViewModel(
            getCategoriesUseCase = get(),
            getTasksUseCase = get(),
            updateTaskStatusUseCase = get(),
            deleteTaskUseCase = get()
        )
    }
    viewModel {
        CreateTaskViewModel(
            getCategoriesUseCase = get(),
            createTaskUseCase = get(),
            getMonthTasksUseCase = get()
        )
    }
    viewModel {
        EditTaskViewModel(
            getCategoriesUseCase = get(),
            editTaskUseCase = get(),
            getMonthTasksUseCase = get(),
            getTaskUseCase = get(),
            savedStateHandle = get()
        )
    }
}