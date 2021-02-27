package com.luisansal.jetpack.features.login

sealed class LoginViewState {
    data class SuccessState(val ok: Boolean) : LoginViewState()
}