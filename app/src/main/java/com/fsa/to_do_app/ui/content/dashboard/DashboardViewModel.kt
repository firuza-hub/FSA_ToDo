package com.fsa.to_do_app.ui.content.dashboard

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsa.to_do_app.domain.model.ActionModel
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.domain.usecase.action.GetActionsUseCase
import com.fsa.to_do_app.domain.usecase.action.UpdateActionStatusUseCase
import com.fsa.to_do_app.domain.usecase.category.GetCategoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getActionsUseCase: GetActionsUseCase,
    private val updateActionStatusUseCase: UpdateActionStatusUseCase
) : ViewModel() {
    private val _actions = MutableStateFlow<List<ActionModel>>(emptyList())
    val actions = _actions.asStateFlow()

    private val _categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        getCategories()
        getActions()
    }

    private fun getActions() {
        viewModelScope.launch {
            _actions.value = getActionsUseCase()
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            _categories.value = getCategoriesUseCase()
        }
    }

    fun onActionChecked(id: Int, checked: Boolean) {
        _actions.value = _actions.value.map {
            if (it.id == id) it.copy(isDone = checked) else it
        }
        viewModelScope.launch {
            updateActionStatusUseCase(id, checked)
        }
    }
}