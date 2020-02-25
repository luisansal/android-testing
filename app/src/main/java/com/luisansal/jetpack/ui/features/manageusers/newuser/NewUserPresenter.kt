package com.luisansal.jetpack.ui.features.manageusers.newuser

import androidx.lifecycle.Observer
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.domain.usecases.UserUseCase
import java.lang.StringBuilder

class NewUserPresenter(private val view: NewUserMVP.View, private val userUseCase: UserUseCase) : NewUserMVP.Presenter {



    override fun newUser(user: User) {
        userUseCase.newUser(user).observe(view,newUserObserver)

//        userUseCase.getUser(user.dni).observe(view, Observer { user ->
//
//
//        })
    }

    private val newUserObserver = Observer<User>{user ->
        view.notifySavedUser(StringBuilder().append(user.name).append(user.lastName).toString())
    }


    override fun getUser(dni: String) {
        userUseCase.getUser(dni).observe(view, Observer { user ->

            user?.let { view.printUser(it) }

        })
    }

    override fun editUser(dni: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}