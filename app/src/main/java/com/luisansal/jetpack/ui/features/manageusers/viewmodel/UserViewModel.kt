package com.luisansal.jetpack.ui.features.manageusers.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisansal.jetpack.domain.usecases.UserUseCase
import com.luisansal.jetpack.ui.features.manageusers.listuser.DeleteUserViewState
import com.luisansal.jetpack.ui.features.manageusers.listuser.ListUserViewState
import kotlinx.coroutines.launch
import java.lang.Exception

class UserViewModel(private val userUseCase: UserUseCase) : ViewModel() {

    var listUserViewState = MutableLiveData<ListUserViewState>()

    var deleteUserViewState = MutableLiveData<DeleteUserViewState>()

    fun deleteUsers(){
        deleteUserViewState.postValue(DeleteUserViewState.LoadingState())

        viewModelScope.launch {
            try {
                deleteUserViewState.postValue(DeleteUserViewState.SuccessState(userUseCase.deleUsers()))

            } catch (e: Exception) {
                deleteUserViewState.postValue(DeleteUserViewState.ErrorState(e))
            }
        }

    }

    fun getUsers() {

        listUserViewState.postValue(ListUserViewState.LoadingState())

        viewModelScope.launch {
            try {
                listUserViewState.postValue(ListUserViewState.SuccessState(userUseCase.getAllUser()))

            } catch (e: Exception) {
                listUserViewState.postValue(ListUserViewState.ErrorState(e))
            }
        }
    }

    fun getUsersPaged() {
        listUserViewState.postValue(ListUserViewState.LoadingState())

        viewModelScope.launch {

            try {
                val users = userUseCase.getAllUserPaged()
                listUserViewState.postValue(ListUserViewState.SuccessPagedState(users))

            } catch (e: Exception) {
                listUserViewState.postValue(ListUserViewState.ErrorState(e))
            }
        }
    }
}
