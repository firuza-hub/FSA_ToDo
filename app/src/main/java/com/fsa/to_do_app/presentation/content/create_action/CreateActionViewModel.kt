package com.fsa.to_do_app.presentation.content.create_action

import androidx.lifecycle.ViewModel
import com.fsa.to_do_app.domain.model.CreateActionModel
import com.fsa.to_do_app.domain.usecase.action.CreateActionUseCase
import com.fsa.to_do_app.domain.usecase.category.GetCategoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreateActionViewModel(
    private val createActionUseCase: CreateActionUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _action = MutableStateFlow(CreateActionModel.NULL)
    val action = _action.asStateFlow()


    fun onContentChange(inputValue:String){
        _action.value = _action.value.copy(content = inputValue)
    }
}