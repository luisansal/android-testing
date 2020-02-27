package com.luisansal.jetpack.ui.features.manageusers.validation

class UserValidation {
    companion object{
        fun validateDni(dni: String) : Boolean{
            return dni.length == 8
        }
    }
}