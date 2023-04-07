package com.fsa.to_do_app.presentation.content.create_action

data class CreateActionErrorsModel(
    val contentError:String?
){
    companion object{
        val NULL = CreateActionErrorsModel(contentError = null)
    }
}
