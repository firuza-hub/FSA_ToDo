package com.fsa.to_do_app.presentation.content.edit_category_list

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsa.to_do_app.domain.model.CategoryModel
import com.fsa.to_do_app.domain.usecase.category.CreateCategoryUseCase
import com.fsa.to_do_app.domain.usecase.category.DeleteCategoryUseCase
import com.fsa.to_do_app.domain.usecase.category.GetCategoriesUseCase
import com.fsa.to_do_app.domain.usecase.category.UpdateCategoryUseCase
import com.fsa.to_do_app.presentation.common.hexToColor
import com.fsa.to_do_app.presentation.common.toHexString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditCategoryListViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
) : ViewModel() {


    val colors = mutableListOf(
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

    private val _editCategoryId = MutableStateFlow<Int?>(null)

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
            if (_editCategoryId.value != null) {
                updateCategoryUseCase(
                    _categories.value.single { it.id == _editCategoryId.value }.copy(
                        name = _newCategoryName.value,
                        colorCode = _newCategoryColor.value.toHexString()
                    )
                )
            } else
                createCategoryUseCase(_newCategoryName.value, _newCategoryColor.value)
            getCategories()
            resetInputValues()
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

    fun selectCategoryForEdit(category: CategoryModel) {
        _newCategoryName.value = category.name
        _newCategoryColor.value = category.colorCode.hexToColor()
        _editCategoryId.value = category.id
        if (!colors.contains(category.colorCode.hexToColor())) {
            colors.add(category.colorCode.hexToColor())
        }
    }

    private fun validateDelete(id: Int): Boolean {
        return true//TODO: add validation logic with db check
    }

    fun resetInputValues() {
        _newCategoryName.value = ""
        _newCategoryColor.value = colors[0]
        _editCategoryId.value = null
    }
}