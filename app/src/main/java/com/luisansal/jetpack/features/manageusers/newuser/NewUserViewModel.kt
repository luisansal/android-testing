package com.luisansal.jetpack.features.manageusers.newuser

import androidx.lifecycle.MutableLiveData
import com.luisansal.jetpack.core.base.BaseViewModel
import com.luisansal.jetpack.core.domain.entity.User
import com.luisansal.jetpack.core.utils.EMPTY
import com.luisansal.jetpack.core.utils.INVALID_INPUT_COLOR
import com.luisansal.jetpack.core.utils.NORMAL_INPUT_COLOR
import com.luisansal.jetpack.core.utils.VALID_INPUT_COLOR
import com.luisansal.jetpack.domain.usecases.LoginUseCase
import com.luisansal.jetpack.domain.usecases.UserUseCase
import com.luisansal.jetpack.features.manageusers.viewmodel.UserViewModel

class NewUserViewModel(
    private val userViewModel: UserViewModel,
    private val userUseCase: UserUseCase,
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {
    var dni = MutableLiveData(String.EMPTY)
    var name = MutableLiveData(String.EMPTY)
    var lastname = MutableLiveData(String.EMPTY)
    var fullName = MutableLiveData(String.EMPTY)
    val goToListado = MutableLiveData(false)
    var dniInputColor = MutableLiveData(NORMAL_INPUT_COLOR)

    fun onClickSiguiente() {
        if (validateDni()) {
            val user = User()
            user.names = name.value ?: String.EMPTY
            user.lastNames = lastname.value ?: String.EMPTY
            user.dni = dni.value ?: String.EMPTY
            fullName.value = "${user.names} ${user.lastNames}"

            userViewModel.newUser(user)
        }

    }

    fun onClickUsuarios() {
        goToListado.value = true
        goToListado.value = null
    }

    fun onClickEliminar() {
        if (validateDni()) {
            dni.postValue(String.EMPTY)
            name.postValue(String.EMPTY)
            lastname.postValue(String.EMPTY)
            userViewModel.deleteUser(dni.value ?: String.EMPTY)
        }
    }

    fun validateDni() =
        if (dni.value?.isEmpty() == true) {
            dniInputColor.postValue(INVALID_INPUT_COLOR)
            false
        } else {
            dniInputColor.postValue(VALID_INPUT_COLOR)
            true
        }


}