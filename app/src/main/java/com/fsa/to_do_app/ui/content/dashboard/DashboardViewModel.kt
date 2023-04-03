package com.fsa.to_do_app.ui.content.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsa.to_do_app.domain.usecase.action.GetActionsUseCase
import com.fsa.to_do_app.domain.usecase.category.GetCategoriesUseCase
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getActionsUseCase: GetActionsUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            val result = getCategoriesUseCase()
            println(result.first())
        }
    }
}