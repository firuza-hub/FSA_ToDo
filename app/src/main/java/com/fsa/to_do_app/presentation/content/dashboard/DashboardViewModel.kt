package com.fsa.to_do_app.presentation.content.dashboard

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsa.to_do_app.domain.model.TaskModel
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.domain.usecase.task.DeleteTaskUseCase
import com.fsa.to_do_app.domain.usecase.task.GetTasksUseCase
import com.fsa.to_do_app.domain.usecase.task.UpdateTaskStatusUseCase
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
    private val _tasks = MutableStateFlow<List<TaskModel>>(emptyList())
    val tasks = _tasks.asStateFlow()

    private val _tasksByCategory = MutableStateFlow<List<TaskModel>>(emptyList())
    val tasksByCategory = _tasksByCategory.asStateFlow()

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
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch {
            getTasksUseCase().collect {

                _isLoading.value = true
                _tasks.value = it
                _isLoading.value = false
            }

        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            _categories.value = getCategoriesUseCase()
        }
    }

    fun onTaskChecked(id: Int, checked: Boolean) {
        _tasks.value = _tasks.value.map {
            if (it.id == id) it.copy(isDone = checked) else it
        }
        _tasksByCategory.value =
            _tasks.value.filter { it.categoryId == _selectedCategory.value.id }
        viewModelScope.launch {
            updateTaskStatusUseCase(id, checked)
        }
    }

    fun updateSelectedCategory(category: CategoryModel) {
        _selectedCategory.value = category
        _tasksByCategory.value = _tasks.value.filter { it.categoryId == category.id }
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