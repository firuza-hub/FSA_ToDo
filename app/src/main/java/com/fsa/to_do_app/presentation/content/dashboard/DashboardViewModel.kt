package com.fsa.to_do_app.presentation.content.dashboard

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsa.to_do_app.domain.model.TaskModel
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.domain.usecase.action.DeleteTaskUseCase
import com.fsa.to_do_app.domain.usecase.action.GetTasksUseCase
import com.fsa.to_do_app.domain.usecase.action.UpdateTaskStatusUseCase
import com.fsa.to_do_app.domain.usecase.category.GetCategoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val updateTaskStatusUseCase: UpdateTaskStatusUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {
    private val _actions = MutableStateFlow<List<TaskModel>>(emptyList())
    val tasks = _actions.asStateFlow()

    private val _actionsByCategory = MutableStateFlow<List<TaskModel>>(emptyList())
    val tasksByCategory = _actionsByCategory.asStateFlow()

    private val _categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _selectedCategory = MutableStateFlow(CategoryModel.NULL)
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    @OptIn(ExperimentalMaterialApi::class)
    private val _categorySheetState = MutableStateFlow(ModalBottomSheetValue.Hidden)

    @OptIn(ExperimentalMaterialApi::class)
    val categorySheetState = _categorySheetState.asStateFlow()


    fun loadData() {
        getCategories()
        getActions()
    }

    private fun getActions() {
        _isLoading.value = true
        viewModelScope.launch {
            _actions.value = getTasksUseCase()
            _isLoading.value = false
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
        _actionsByCategory.value =
            _actions.value.filter { it.categoryId == _selectedCategory.value.id }
        viewModelScope.launch {
            updateTaskStatusUseCase(id, checked)
        }
    }

    fun updateSelectedCategory(category: CategoryModel) {
        _selectedCategory.value = category
        _actionsByCategory.value = _actions.value.filter { it.categoryId == category.id }
    }

    @OptIn(ExperimentalMaterialApi::class)
    fun updateCategorySheetState(sheetState: ModalBottomSheetValue) {
        _categorySheetState.value = sheetState
    }

    fun delete(task: TaskModel, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTaskUseCase(task)

            withContext(Dispatchers.Main) { onSuccess() }
        }
    }
}