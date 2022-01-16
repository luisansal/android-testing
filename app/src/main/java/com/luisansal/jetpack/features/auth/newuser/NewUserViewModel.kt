package com.luisansal.jetpack.features.auth.newuser

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import com.luisansal.jetpack.core.base.BaseViewModel
import com.luisansal.jetpack.domain.usecases.UserUseCase
import kotlinx.coroutines.launch

class NewUserViewModel(private val activity: Activity, private val userUseCase: UserUseCase) : BaseViewModel() {

    var email = "user2@gmail.com"
    var password = "123456"
    var userSaved = MutableLiveData(false)

    fun onClickSave() {
        showLoading.value = true
        uiScope.launch {
            userUseCase.newAuthUser(activity, email, password, {
                userSaved.postValue(true)
                showLoading.postValue(false)
            }) {
                errorDialog.postValue(it)
                showLoading.postValue(false)
            }
        }
    }
}