package com.luisansal.jetpack.features.auth.login

import android.app.Activity
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.luisansal.jetpack.R
import com.luisansal.jetpack.core.base.BaseViewModel
import com.luisansal.jetpack.core.utils.EMPTY
import com.luisansal.jetpack.domain.usecases.LoginFirebaseUseCase
import com.luisansal.jetpack.features.auth.LoggedInUserView
import com.luisansal.jetpack.features.auth.LoginFormState
import com.luisansal.jetpack.features.auth.LoginResult
import kotlinx.coroutines.launch

class LoginViewModelFirebase(private val activity: Activity, private val loginUseCase: LoginFirebaseUseCase) : BaseViewModel() {

    val goToNewUser = MutableLiveData(false)

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult?>()
    val loginResult: LiveData<LoginResult?> = _loginResult

    var email = "luis@gmail.com"
    var password = "123456"

    fun onClickNewUser() {
        goToNewUser.value = true
        goToNewUser.value = null
        _loginResult.value = null
    }

    fun login(email: String, password: String) {
        uiScope.launch {
            loginUseCase.login(activity, email, password, {
                _loginResult.postValue(LoginResult(success = LoggedInUserView(it?.displayName ?: String.EMPTY)))
            }) {
                _loginResult.postValue(LoginResult(error = R.string.email_or_username_incorrect))
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

}