package com.luisansal.jetpack.ui.features.manageusers.newuser

import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.domain.exception.DniValidationException
import com.luisansal.jetpack.domain.usecases.UserUseCase
import com.luisansal.jetpack.ui.features.manageusers.validation.UserValidation
import java.lang.StringBuilder

class NewUserPresenter(private val view: NewUserMVP.View, private val userUseCase: UserUseCase) : NewUserMVP.Presenter {
    override fun deleteUser(dni: String) {
        userUseCase.deleUser(dni)
        view.notifyUserDeleted()
        view.resetView()
    }

    @Throws(DniValidationException::class)
    override fun newUser(user: User) {
        if (!UserValidation.validateDni(user.dni))
            throw DniValidationException()

        view.notifyUserSaved(userUseCase.newUser(user))
    }

    override fun getUser(dni: String) {
        val user = userUseCase.getUser(dni)
        user?.let { view.printUser(it) }
    }

    override fun editUser(dni: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}