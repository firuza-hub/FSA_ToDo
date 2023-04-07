package com.fsa.to_do_app.presentation.content.create_action

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.domain.model.CreateActionModel
import com.fsa.to_do_app.domain.usecase.action.CreateActionUseCase
import com.fsa.to_do_app.domain.usecase.category.GetCategoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateActionViewModel(
    private val createActionUseCase: CreateActionUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _action = MutableStateFlow(CreateActionModel.NULL)
    val action = _action.asStateFlow()

    private val _categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _validationErrors = MutableSharedFlow<CreateActionErrorsModel>()
    val validationErrors = _validationErrors.asSharedFlow()

    init {
        getCategories()
    }

    fun save(onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {

            if (inputValidated()) {
                createActionUseCase(_action.value)
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
}