package com.luisansal.jetpack.ui.features.manageusers.newuser

import androidx.lifecycle.LifecycleOwner
import com.luisansal.jetpack.domain.entity.User

interface NewUserMVP {
    interface View : LifecycleOwner{
        fun printUser(user : User)
        fun onClickBtnSiguiente()
        fun notifySavedUser(name: String)
        fun onTextDniChanged()
        fun onClickBtnListado()
    }

    interface Presenter{
        fun newUser(user: User)
        fun getUser(dni: String)
        fun editUser(dni: String)
    }
}