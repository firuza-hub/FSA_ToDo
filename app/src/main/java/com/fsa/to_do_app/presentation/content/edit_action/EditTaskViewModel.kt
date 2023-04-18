package com.fsa.to_do_app.presentation.content.edit_action

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsa.to_do_app.domain.model.TaskModel
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.domain.usecase.task.GetMonthTasksUseCase
import com.fsa.to_do_app.domain.usecase.category.GetCategoriesUseCase
import com.fsa.to_do_app.domain.usecase.task.EditTaskUseCase
import com.fsa.to_do_app.domain.usecase.task.GetTaskUseCase
import com.fsa.to_do_app.presentation.content.create_action.CalendarState
import com.fsa.to_do_app.presentation.content.create_action.CreateActionErrorsModel
import com.fsa.to_do_app.util.stringToAmPm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class EditTaskViewModel(
    private val editTaskUseCase: EditTaskUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getMonthTasksUseCase: GetMonthTasksUseCase,
    private val getTaskUseCase: GetTaskUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val taskId: Int = checkNotNull( savedStateHandle["id"])
    private val cal = Calendar.getInstance(TimeZone.getDefault())
    private val _task = MutableStateFlow(TaskModel.NULL)
    val task = _task.asStateFlow()

    private val _categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _calendar = MutableStateFlow(CalendarState.setCalendar(cal, emptyList()))
    val calendar = _calendar.asStateFlow()

    private val _validationErrors = MutableSharedFlow<CreateActionErrorsModel>()
    val validationErrors = _validationErrors.asSharedFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {
        _loading.value = true
        getTask(taskId)
        getCategories()
        initCalendar()
        _loading.value = false
    }

    private fun getTask(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _task.value = getTaskUseCase(id)
        }
    }

    fun save(onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {

            if (inputValidated()) {
                editTaskUseCase(_task.value)
                withContext(Dispatchers.Main) { onSuccess() }
            }
        }
    }

    private suspend fun inputValidated(): Boolean {
        if (_task.value.content.isBlank()) {
            _validationErrors.emit(CreateActionErrorsModel(contentError = "Please fill the content"))
            return false
        }
        return true

    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _categories.value = getCategoriesUseCase()
        }
    }

    fun onContentChange(inputValue: String) {
        _task.value = _task.value.copy(content = inputValue)
    }

    fun selectCategory(categoryModel: CategoryModel) {
        _task.value = _task.value.copy(category = categoryModel)
    }

    fun selectDate(date: Date) {
        _task.value = _task.value.copy(date = date)
    }
    fun selectTime(hour:Int, minute:Int, ap:String) {
         cal.set(Calendar.HOUR, hour)
         cal.set(Calendar.MINUTE, minute)
         cal.set(Calendar.AM_PM, ap.stringToAmPm())
        _task.value = _task.value.copy(date = cal.time)
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

    private suspend fun getMonthTasks(month: Int, year: Int): List<TaskModel> {
        return getMonthTasksUseCase(month, year)
    }

    fun initCalendar(){
        viewModelScope.launch(Dispatchers.IO) {
            val tasks = getMonthTasks(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR))
            _calendar.value = CalendarState.setCalendar(cal, tasks)
        }
    }
}