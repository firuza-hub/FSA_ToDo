package com.fsa.to_do_app.presentation.content.edit_category_list

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.domain.usecase.category.CreateCategoryUseCase
import com.fsa.to_do_app.domain.usecase.category.DeleteCategoryUseCase
import com.fsa.to_do_app.domain.usecase.category.GetCategoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditCategoryListViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
) : ViewModel() {


    val colors = listOf(
        Color(0xFFEF9A9A),
        Color(0xFFF48FB1),
        Color(0xFF80CBC4),
        Color(0xFFA5D6A7),
        Color(0xFFFFCC80),
        Color(0xFFFFAB91),
        Color(0xFF81D4FA),
        Color(0xFFCE93D8),
        Color(0xFFB39DDB)
    )

    private val _categories = MutableStateFlow<List<CategoryModel>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _newCategoryName = MutableStateFlow("")
    val newCategoryName = _newCategoryName.asStateFlow()

    private val _newCategoryColor = MutableStateFlow(colors[0])
    val newCategoryColor = _newCategoryColor.asStateFlow()

    init {
        getCategories()
    }


    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _categories.value =
                getCategoriesUseCase.invoke(showAll = true)
        }
    }

    fun createNewCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            createCategoryUseCase(_newCategoryName.value, _newCategoryColor.value)
            getCategories()
        }
    }

    fun updateCategoryName(input: String) {
        _newCategoryName.value = input
    }

    fun updateCategoryColor(input: Color) {
        _newCategoryColor.value = input
    }

    fun deleteCategory(category: CategoryModel) {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateDelete(category.id)) {
                deleteCategoryUseCase(category)
                getCategories()
            }
        }
    }

    private fun validateDelete(id: Int): Boolean {
        return true//TODO: add validation logic with db check
    }
}