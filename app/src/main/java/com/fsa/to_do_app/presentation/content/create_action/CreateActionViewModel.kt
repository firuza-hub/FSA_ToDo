package com.fsa.to_do_app.presentation.content.create_action

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsa.to_do_app.domain.model.ActionModel
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.domain.model.CreateActionModel
import com.fsa.to_do_app.domain.usecase.action.CreateTaskUseCase
import com.fsa.to_do_app.domain.usecase.action.GetMonthTasksUseCase
import com.fsa.to_do_app.domain.usecase.category.GetCategoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class CreateActionViewModel(
    private val createTaskUseCase: CreateTaskUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getMonthTasksUseCase: GetMonthTasksUseCase
) : ViewModel() {

    private val cal = Calendar.getInstance(TimeZone.getDefault())
    private val _action = MutableStateFlow(CreateActionModel.NULL)
    val action = _action.asStateFlow()

    private val _categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _calendar = MutableStateFlow(CalendarState.setCalendar(cal, emptyList()))
    val calendar = _calendar.asStateFlow()

    private val _validationErrors = MutableSharedFlow<CreateActionErrorsModel>()
    val validationErrors = _validationErrors.asSharedFlow()

    init {
        getCategories()
        initCalendar()
    }

    fun save(onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {

            if (inputValidated()) {
                createTaskUseCase(_action.value)
                withContext(Dispatchers.Main) { onSuccess() }
            }
        }
    }

    private suspend fun inputValidated(): Boolean {
        if (_action.value.content.isBlank()) {
            _validationErrors.emit(CreateActionErrorsModel(contentError = "Please fill the content"))
            return false
        }
        return true

    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _categories.value = getCategoriesUseCase()
            _action.value = _action.value.copy(category = _categories.value.first())
        }
    }

    fun onContentChange(inputValue: String) {
        _action.value = _action.value.copy(content = inputValue)
    }

    fun selectCategory(categoryModel: CategoryModel) {
        _action.value = _action.value.copy(category = categoryModel)
    }

    fun selectDate(date: Date) {
        _action.value = _action.value.copy(date = date)
    }

    fun onMonthUp() {
        viewModelScope.launch(Dispatchers.IO) {
            cal.add(Calendar.MONTH, 1)
            val tasks = getMonthTasks(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR))
            _calendar.value = CalendarState.setCalendar(cal, tasks)
        }
    }

    fun onMonthDown() {
        viewModelScope.launch(Dispatchers.IO) {
            cal.add(Calendar.MONTH, -1)
            val tasks =  getMonthTasks(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR))
            _calendar.value = CalendarState.setCalendar(cal, tasks)
        }
    }

    private suspend fun getMonthTasks(month: Int, year: Int): List<ActionModel> {
        return getMonthTasksUseCase(month, year)
    }

    fun initCalendar(){
        viewModelScope.launch(Dispatchers.IO) {
            val tasks = getMonthTasks(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR))
            _calendar.value = CalendarState.setCalendar(cal, tasks)
        }
    }
}