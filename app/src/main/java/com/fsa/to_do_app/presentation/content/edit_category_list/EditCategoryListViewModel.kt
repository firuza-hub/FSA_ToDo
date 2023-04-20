package com.fsa.to_do_app.presentation.content.edit_category_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.domain.usecase.category.GetCategoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditCategoryListViewModel(private val getCategoriesUseCase : GetCategoriesUseCase):ViewModel() {

    private val _categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val categories = _categories.asStateFlow()
    init{
        getCategories()
    }


    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _categories.value =
                getCategoriesUseCase.invoke(showAll = true)
        }
    }
}