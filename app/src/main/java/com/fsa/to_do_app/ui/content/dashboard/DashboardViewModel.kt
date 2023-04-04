package com.fsa.to_do_app.ui.content.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsa.to_do_app.data.local.entities.Action
import com.fsa.to_do_app.data.local.entities.Category
import com.fsa.to_do_app.domain.usecase.action.GetActionsUseCase
import com.fsa.to_do_app.domain.usecase.category.GetCategoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getActionsUseCase: GetActionsUseCase
) : ViewModel() {

    private val _actions = MutableStateFlow<List<Action>>(emptyList())
    val actions = _actions.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        getCategories()
        getActions()
    }

    fun getActions(){
        viewModelScope.launch {
            _actions.value = getActionsUseCase()
        }
    }

    fun getCategories(){

        viewModelScope.launch {
            _categories.value = getCategoriesUseCase()
        }
    }
}