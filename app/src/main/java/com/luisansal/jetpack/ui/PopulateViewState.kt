package com.luisansal.jetpack.ui

sealed class PopulateViewState  {
    data class ErrorState(val error: Throwable?) : PopulateViewState()
    data class LoadingState(val a: Int = 0) : PopulateViewState()
    data class SuccessState(val data: Boolean) : PopulateViewState()
}