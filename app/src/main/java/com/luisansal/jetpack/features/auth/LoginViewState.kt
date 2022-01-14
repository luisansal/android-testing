package com.luisansal.jetpack.features.auth

sealed class LoginViewState {
    data class LogoutSuccessState(val ok: Boolean) : LoginViewState()
}