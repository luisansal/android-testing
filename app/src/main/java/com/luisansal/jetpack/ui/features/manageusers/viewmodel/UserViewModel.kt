package com.luisansal.jetpack.ui.features.manageusers.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.domain.usecases.UserUseCase
import com.luisansal.jetpack.ui.features.manageusers.newuser.UserViewState
import kotlinx.coroutines.launch
import java.lang.Exception

class UserViewModel(private val userUseCase: UserUseCase) : ViewModel() {

    val listUserViewState = MutableLiveData<UserViewState>()
    val deleteUserViewState = MutableLiveData<UserViewState>()
    val userViewState = MutableLiveData<UserViewState>()

    companion object{
        var user : User? = null
    }

    fun deleteUsers() {
        deleteUserViewState.postValue(UserViewState.LoadingState())

        viewModelScope.launch {
            try {
                deleteUserViewState.postValue(UserViewState.DeleteSuccessState(userUseCase.deleUsers()))

            } catch (e: Exception) {
                deleteUserViewState.postValue(UserViewState.ErrorState(e))
            }
        }

    }

    fun getUser() {
        userViewState.postValue(UserViewState.LoadingState())

        viewModelScope.launch {
            try {
                userViewState.postValue(UserViewState.CrearGetSuccessState(user))
            } catch (e: Exception) {
                userViewState.postValue(UserViewState.ErrorState(e))
            }
        }
    }

    fun getUser(dni: String) {

        userViewState.postValue(UserViewState.LoadingState())

        viewModelScope.launch {
            try {
                userViewState.postValue(userUseCase.getUser(dni)?.let { UserViewState.CrearGetSuccessState(it) })

            } catch (e: Exception) {
                userViewState.postValue(UserViewState.ErrorState(e))
            }
        }
    }

    fun getUsers() {
        listUserViewState.postValue(UserViewState.LoadingState())

        viewModelScope.launch {
            try {
                listUserViewState.postValue(UserViewState.ListSuccessState(userUseCase.getAllUser()))

            } catch (e: Exception) {
                listUserViewState.postValue(UserViewState.ErrorState(e))
            }
        }
    }

    fun getUsersPaged() {
        listUserViewState.postValue(UserViewState.LoadingState())

        viewModelScope.launch {

            try {
                val users = userUseCase.getAllUserPaged()
                listUserViewState.postValue(UserViewState.ListSuccessPagedState(users))

            } catch (e: Exception) {
                listUserViewState.postValue(UserViewState.ErrorState(e))
            }
        }
    }
}
