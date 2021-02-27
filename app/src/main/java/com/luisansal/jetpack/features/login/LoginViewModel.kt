package com.luisansal.jetpack.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.luisansal.jetpack.R
import com.luisansal.jetpack.domain.usecases.LoginUseCase
import com.luisansal.jetpack.data.Result
import com.luisansal.jetpack.domain.exceptions.ConnectException
import com.luisansal.jetpack.domain.exceptions.UnauthorizedException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    val loginViewState = MutableLiveData<LoginViewState>()

    fun login(username: String, password: String) {
        viewModelScope.launch {

            when (val result = loginUseCase.login(username, password)) {
                is Result.Success -> {
                    _loginResult.value = LoginResult(success = result.data?.names?.let { LoggedInUserView(displayName = it) })
                }
                is Result.Error -> {
                    when (result.exception) {
                        is UnauthorizedException -> {
                            _loginResult.value = LoginResult(error = R.string.email_or_username_incorrect)
                        }
                        is ConnectException -> {
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
        CoroutineScope(Dispatchers.Main).launch {
            loginUseCase.logout()
            loginViewState.postValue(LoginViewState.SuccessState(true))
        }
    }
}