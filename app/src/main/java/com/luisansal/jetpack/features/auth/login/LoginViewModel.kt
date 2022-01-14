package com.luisansal.jetpack.features.auth.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.luisansal.jetpack.R
import com.luisansal.jetpack.core.base.BaseViewModel
import com.luisansal.jetpack.core.data.Result
import com.luisansal.jetpack.core.domain.exceptions.ConnectException
import com.luisansal.jetpack.core.domain.exceptions.UnauthorizedException
import com.luisansal.jetpack.domain.usecases.LoginUseCase
import com.luisansal.jetpack.features.auth.LoggedInUserView
import com.luisansal.jetpack.features.auth.LoginFormState
import com.luisansal.jetpack.features.auth.LoginResult
import com.luisansal.jetpack.features.auth.LoginViewState
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class LoginViewModel(private val loginUseCase: LoginUseCase) : BaseViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    val loginViewState = MutableLiveData<LoginViewState>()

    fun login(username: String, password: String) {
        uiScope.launch {

            when (val result = loginUseCase.login(username, password)) {
                is Result.Success -> {
                    _loginResult.value = LoginResult(success = result.data?.names?.let { LoggedInUserView(displayName = it) })
                }
                is Result.Error -> {
                    when (result.exception) {
                        is UnauthorizedException -> {
                            _loginResult.value = LoginResult(error = R.string.email_or_username_incorrect)
                        }
                        is ConnectException, is UnknownHostException -> {
                            _loginResult.value = LoginResult(error = R.string.not_internet_connection)
                        }
                    }
                }
            }
        }

    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    fun logout() {
        uiScope.launch {
            loginUseCase.logout()
            loginViewState.postValue(LoginViewState.LogoutSuccessState(true))
        }
    }
}