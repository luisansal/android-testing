package com.luisansal.jetpack.ui.features.manageusers.listuser

sealed class DeleteUserViewState  {
    data class ErrorState(val error: Throwable?) : DeleteUserViewState()
    data class LoadingState(val a: Int = 0) : DeleteUserViewState()
    data class SuccessState(val data: Boolean) : DeleteUserViewState()
}