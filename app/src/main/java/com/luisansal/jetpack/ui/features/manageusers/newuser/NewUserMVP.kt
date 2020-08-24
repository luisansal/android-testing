package com.luisansal.jetpack.ui.features.manageusers.newuser

import androidx.lifecycle.LifecycleOwner
import com.luisansal.jetpack.domain.entity.User

interface NewUserMVP {
    interface View : LifecycleOwner{
        fun printUser(user : User)
        fun onClickBtnSiguiente()
        fun notifyUserDeleted()
        fun notifyUserValidationConstraint()
        fun onClickBtnListado()
        fun resetView()
        fun notifyDniUserValidationConstraint()
        fun nextStep(user: User)
        fun notifyUserSaved(user: User)
    }

    interface Presenter{
        fun newUser(user: User)
    }
}