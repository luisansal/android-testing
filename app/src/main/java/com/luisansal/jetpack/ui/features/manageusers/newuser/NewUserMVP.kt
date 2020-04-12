package com.luisansal.jetpack.ui.features.manageusers.newuser

import androidx.lifecycle.LifecycleOwner
import com.luisansal.jetpack.domain.entity.User

interface NewUserMVP {
    interface View : LifecycleOwner{
        fun printUser(user : User)
        fun onClickBtnSiguiente()
        fun notifyUserSaved(user: User)
        fun notifyUserDeleted()
        fun notifyUserValidationConstraint()
        fun onClickBtnListado()
        fun resetView()
    }

    interface Presenter{
        fun newUser(user: User)
    }
}