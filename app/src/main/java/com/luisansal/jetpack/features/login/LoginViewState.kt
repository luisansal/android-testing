package com.luisansal.jetpack.features.login

sealed class LoginViewState {
    data class LogoutSuccessState(val ok: Boolean) : LoginViewState()
}