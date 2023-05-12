package com.fsa.to_do_app.presentation.content.dashboard

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.domain.model.TaskModel
import com.fsa.to_do_app.domain.usecase.category.GetCategoriesUseCase
import com.fsa.to_do_app.domain.usecase.task.DeleteTaskUseCase
import com.fsa.to_do_app.domain.usecase.task.GetTasksUseCase
import com.fsa.to_do_app.domain.usecase.task.UpdateTaskStatusUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

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

    private val _filterDate = MutableStateFlow(Calendar.getInstance())
    val filterDate = _filterDate.asStateFlow()

    private val _categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _selectedCategory = MutableStateFlow(CategoryModel.NULL)
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _allShown = MutableStateFlow(DashboardFilter.ShowToday)
    val allShown = _allShown.asStateFlow()
    private lateinit var job: Job
    fun loadData() {
        getCategories()
        getTasks()
    }

    private fun getTasks() {
        when (_allShown.value) {
            DashboardFilter.ShowAll -> {
                job = viewModelScope.launch(Dispatchers.IO) {
                    getTasksUseCase.invoke(showAll = true).collect {
                        Log.d("TASKS_COLLECTED", "ShowAll " + it.firstOrNull()?.content.toString())
                        _isLoading.value = true
                        _tasks.value = it
                        _isLoading.value = false
                    }
                }
            }
            DashboardFilter.ShowToday -> {
                job = viewModelScope.launch(Dispatchers.IO) {
                    getTasksUseCase.invoke(showAll = false).collect {
                        Log.d(
                            "TASKS_COLLECTED",
                            "ShowToday " + it.firstOrNull()?.content.toString()
                        )
                        _isLoading.value = true
                        _tasks.value = it
                        _isLoading.value = false
                    }
                }
            }
            DashboardFilter.ShowByDate -> {
                job = viewModelScope.launch(Dispatchers.IO) {
                    getTasksUseCase.invoke(showAll = false, date = _filterDate.value.time)
                        .collect {
                            Log.d(
                                "TASKS_COLLECTED",
                                "ShowByDate " + it.firstOrNull()?.content.toString()
                            )
                            _isLoading.value = true
                            _tasks.value = it
                            _isLoading.value = false
                        }
                }
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _categories.value =
                getCategoriesUseCase.invoke(showAll = _allShown.value == DashboardFilter.ShowAll)
        }
    }

    fun onTaskChecked(id: Int, checked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            updateTaskStatusUseCase(id, checked)
        }
    }

    fun updateSelectedCategory(category: CategoryModel) {
        _selectedCategory.value = category
        _tasksByCategory.value = _tasks.value.filter { it.category.id == category.id }
    }


    fun delete(task: TaskModel, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTaskUseCase(task)
            withContext(Dispatchers.Main) { onSuccess() }
        }
    }

    fun showToday() {
        job.cancel()
        Log.d("TASKS_COLLECTED", "SET SHOW TODAY ")
        _allShown.value = DashboardFilter.ShowToday
        loadData()
    }

    fun showAll() {
        job.cancel()
        _allShown.value = DashboardFilter.ShowAll
        loadData()
    }

    fun showByDate(filterCalendar: Calendar) {
        _filterDate.value = filterCalendar
        job.cancel()
        _allShown.value = DashboardFilter.ShowByDate
        loadData()
    }
}