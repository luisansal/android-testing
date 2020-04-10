package com.luisansal.jetpack.ui.features.manageusers.newuser

import com.luisansal.jetpack.domain.entity.User

sealed class UserViewState  {
    data class ErrorState(val error: Throwable?) : UserViewState()
    data class LoadingState(val a: Int = 0) : UserViewState()
    data class SuccessState(val user: User?) : UserViewState()
}