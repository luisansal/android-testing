package com.luisansal.jetpack.features.manageusers.newuser

import androidx.lifecycle.MutableLiveData
import com.luisansal.jetpack.core.base.BaseViewModel
import com.luisansal.jetpack.core.domain.entity.User
import com.luisansal.jetpack.core.utils.EMPTY
import com.luisansal.jetpack.core.utils.INVALID_INPUT_COLOR
import com.luisansal.jetpack.core.utils.NORMAL_INPUT_COLOR
import com.luisansal.jetpack.core.utils.VALID_INPUT_COLOR
import com.luisansal.jetpack.features.manageusers.viewmodel.UserViewModel

class NewUserViewModel(
    private val userViewModel: UserViewModel,
) : BaseViewModel() {
    var dni = MutableLiveData("05159410")
    var names = MutableLiveData(String.EMPTY)
    var lastnames = MutableLiveData(String.EMPTY)
    var fullName = MutableLiveData(String.EMPTY)
    val goToListado = MutableLiveData(false)
    var dniInputColor = MutableLiveData(NORMAL_INPUT_COLOR)

    fun onClickSiguiente() {
        if (validateDni()) {
            val user = User()
            user.names = names.value ?: String.EMPTY
            user.lastNames = lastnames.value ?: String.EMPTY
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
            names.postValue(String.EMPTY)
            lastnames.postValue(String.EMPTY)
            userViewModel.deleteUser(dni.value ?: String.EMPTY)
        }
    }

    private fun validateDni() =
        if (dni.value?.isEmpty() == true) {
            dniInputColor.postValue(INVALID_INPUT_COLOR)
            false
        } else {
            dniInputColor.postValue(VALID_INPUT_COLOR)
            true
        }

    fun onClickBtnBuscar(){
        userViewModel.getUser(dni.value ?: EMPTY)
    }

    fun fillFields(user: User?) {
        user?.also {
            dni.postValue(user.dni)
            names.postValue(user.names)
            lastnames.postValue(user.lastNames)
        }
    }
}