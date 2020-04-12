package com.luisansal.jetpack.ui.viewstate

sealed class BaseViewState{
    data class ErrorState(val error: Throwable?) : BaseViewState()
    data class LoadingState(val a: Int = 0) : BaseViewState()
    data class SuccessState<T>(val data: T?) : BaseViewState()
}