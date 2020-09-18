package com.luisansal.jetpack.features.manageusers.validation

import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.domain.usecases.UserUseCase

class UserValidation(private val userUserUseCase: UserUseCase) {
    companion object {
        fun validateUserToCreate(user: User): Boolean {
            return user.dni.length == 8 && !user.name.equals("") && !user.lastName.equals("")
        }
        fun validateDni(dni: String): Boolean {
            return dni.length == 8
        }
    }
}