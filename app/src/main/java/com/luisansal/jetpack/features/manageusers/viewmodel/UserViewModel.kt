package com.luisansal.jetpack.features.manageusers.viewmodel

import androidx.lifecycle.MutableLiveData
import com.luisansal.jetpack.core.base.BaseViewModel
import com.luisansal.jetpack.core.domain.entity.User
import com.luisansal.jetpack.core.domain.exceptions.CreateUserValidationException
import com.luisansal.jetpack.core.domain.exceptions.DniValidationException
import com.luisansal.jetpack.core.domain.exceptions.UserExistException
import com.luisansal.jetpack.domain.analytics.TagAnalytics
import com.luisansal.jetpack.domain.usecases.UserUseCase
import com.luisansal.jetpack.features.analytics.FirebaseanalyticsViewModel
import com.luisansal.jetpack.features.manageusers.UserViewState
import com.luisansal.jetpack.features.manageusers.validation.UserValidation
import kotlinx.coroutines.launch

class UserViewModel(
    private val userUseCase: UserUseCase,
    private val firebaseanalyticsViewModel: FirebaseanalyticsViewModel
) : BaseViewModel() {

    val listUserViewState = MutableLiveData<UserViewState>()
    val deleteUserViewState = MutableLiveData<UserViewState>()
    val userViewState = MutableLiveData<UserViewState>()

    companion object {
        var user: User? = null
    }

    val user = MutableLiveData<User>()

    fun newUser(user: User) {
        if (!UserValidation.validateDni(user.dni)) {
            errorDialog.postValue(DniValidationException())
        }

        uiScope.launch {
            val userExist = userUseCase.getUser(user.dni)
            if (userExist !== null) {
                errorDialog.postValue(UserExistException(userExist))
            }

            if (!UserValidation.validateUserToCreate(user)) {
                errorDialog.postValue(CreateUserValidationException())
            }

            try {
                userUseCase.newUser(user)
                userViewState.postValue(UserViewState.NewUserSuccess(user))
            } catch (e: Exception) {
                errorDialog.postValue(e)
            }

            firebaseanalyticsViewModel.enviarEvento(TagAnalytics.EVENTO_CREAR_USUARIO)
        }

    }

    fun deleteUsers() {
        deleteUserViewState.postValue(UserViewState.LoadingState(true))
        uiScope.launch {
            try {
                deleteUserViewState.postValue(UserViewState.DeleteAllSuccessState(userUseCase.deleUsers()))
            } catch (e: Exception) {
                deleteUserViewState.postValue(UserViewState.ErrorState(e))
            }
            deleteUserViewState.postValue(UserViewState.LoadingState(false))
        }

    }

    fun deleteUser(dni: String) {
        deleteUserViewState.postValue(UserViewState.LoadingState(true))

        uiScope.launch {
            try {
                deleteUserViewState.postValue(UserViewState.DeleteSuccessState(userUseCase.deleUser(dni)))

            } catch (e: Exception) {
                deleteUserViewState.postValue(UserViewState.ErrorState(e))
            }
            deleteUserViewState.postValue(UserViewState.LoadingState(false))
        }
    }

    fun getUser() {
        userViewState.value = UserViewState.LoadingState(true)

        uiScope.launch {
            try {
                userViewState.postValue(UserViewState.GetUserSuccessState(user.value))
            } catch (e: Exception) {
                userViewState.postValue(UserViewState.ErrorState(e))
            }
            userViewState.value = UserViewState.LoadingState(false)
        }
    }

    fun getUser(dni: String) {

        userViewState.value = UserViewState.LoadingState(true)

        uiScope.launch {
            try {
                userViewState.postValue(UserViewState.GetUserSuccessState(userUseCase.getUser(dni)))

            } catch (e: Exception) {
                userViewState.postValue(UserViewState.ErrorState(e))
            }
            userViewState.value = UserViewState.LoadingState(false)
        }
    }

    fun getUsers() {
        listUserViewState.postValue(UserViewState.LoadingState(true))

        uiScope.launch {
            try {
                listUserViewState.postValue(UserViewState.ListSuccessState(userUseCase.getAllUser()))

            } catch (e: Exception) {
                listUserViewState.postValue(UserViewState.ErrorState(e))
            }
            listUserViewState.postValue(UserViewState.LoadingState(false))
        }
    }

    fun getUsersPaged() {
        listUserViewState.value = UserViewState.LoadingState(true)
        uiScope.launch {
            try {
                val users = userUseCase.getAllUserPaged()
                listUserViewState.postValue(UserViewState.ListSuccessPagedState(users))

            } catch (e: Exception) {
                listUserViewState.postValue(UserViewState.ErrorState(e))
            }
            listUserViewState.value = UserViewState.LoadingState(false)
        }
    }

    fun getByNamesPaged(names: String) {

        uiScope.launch {
            try {
                val users = userUseCase.getByNamesPaged(names)
                listUserViewState.postValue(UserViewState.ListSuccessPagedState(users))
            } catch (e: Exception) {
                listUserViewState.postValue(UserViewState.ErrorState(e))
            }
        }
    }
}
