package com.fsa.to_do_app.presentation.content.create_action

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.domain.model.CreateActionModel
import com.fsa.to_do_app.domain.usecase.action.CreateActionUseCase
import com.fsa.to_do_app.domain.usecase.category.GetCategoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateActionViewModel(
    private val createActionUseCase: CreateActionUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _action = MutableStateFlow(CreateActionModel.NULL)
    val action = _action.asStateFlow()

    private val _categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            _categories.value = getCategoriesUseCase()
            _action.value = _action.value.copy(category =  _categories.value.first())
        }
    }
    fun onContentChange(inputValue:String){
        _action.value = _action.value.copy(content = inputValue)
    }

    fun selectCategory(categoryModel: CategoryModel){
        _action.value = _action.value.copy(category = categoryModel)
    }
}