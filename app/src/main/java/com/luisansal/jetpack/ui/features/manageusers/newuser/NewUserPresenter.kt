package com.luisansal.jetpack.ui.features.manageusers.newuser

import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.domain.usecases.UserUseCase
import java.lang.StringBuilder

class NewUserPresenter(private val view: NewUserMVP.View, private val userUseCase: UserUseCase) : NewUserMVP.Presenter {

    override fun newUser(user: User) {
        userUseCase.newUser(user)
        view.notifyUserSaved(StringBuilder().append(user.name).append(" ").append(user.lastName).toString())
    }

    override fun getUser(dni: String) {11
        val user = userUseCase.getUser(dni)
        user?.let { view.printUser(it) }
    }

    override fun editUser(dni: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}