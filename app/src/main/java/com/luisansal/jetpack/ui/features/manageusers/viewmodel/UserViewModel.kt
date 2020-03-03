package com.luisansal.jetpack.ui.features.manageusers.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luisansal.jetpack.domain.usecases.UserUseCase
import com.luisansal.jetpack.ui.features.manageusers.listuser.ListUserViewState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class UserViewModel(private val userUseCase: UserUseCase) : ViewModel() {

    var listUserViewState = MutableLiveData<ListUserViewState>()

    fun getUsers() {

        listUserViewState.postValue(ListUserViewState.LoadingState())

        GlobalScope.launch {

            try {
                listUserViewState.postValue(ListUserViewState.SuccessState(userUseCase.getAllUser()))

            } catch (e: Exception) {
                listUserViewState.postValue(ListUserViewState.ErrorState(e))
            }

        }

    }

    fun getUsersPaged() {

        listUserViewState.postValue(ListUserViewState.LoadingState())

        GlobalScope.launch {

            try {
                val users = userUseCase.getAllUserPaged()
                listUserViewState.postValue(ListUserViewState.SuccessPagedState(users))

            } catch (e: Exception) {
                listUserViewState.postValue(ListUserViewState.ErrorState(e))
            }

        }

    }


}
