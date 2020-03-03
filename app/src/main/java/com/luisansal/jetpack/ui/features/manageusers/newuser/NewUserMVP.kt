package com.luisansal.jetpack.ui.features.manageusers.newuser

import androidx.lifecycle.LifecycleOwner
import com.luisansal.jetpack.domain.entity.User
import android.graphics.Movie



interface NewUserMVP {
    interface View : LifecycleOwner{
        fun printUser(user : User)
        fun onClickBtnSiguiente()
        fun onClickBtnEliminar()
        fun notifyUserSaved(name: String)
        fun notifyUserDeleted()
        fun notifyUserValidationConstraint()
        fun onTextDniChanged()
        fun onClickBtnListado()
        fun resetView()
    }

    interface Presenter{
        fun newUser(user: User)
        fun getUser(dni: String)
        fun deleteUser(dni:String)
        fun editUser(dni: String)
    }
}