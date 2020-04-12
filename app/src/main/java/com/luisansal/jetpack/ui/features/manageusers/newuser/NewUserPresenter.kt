package com.luisansal.jetpack.ui.features.manageusers.newuser

import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.domain.exception.DniValidationException
import com.luisansal.jetpack.domain.usecases.UserUseCase
import com.luisansal.jetpack.ui.features.manageusers.validation.UserValidation

class NewUserPresenter(private val view: NewUserMVP.View, private val userUseCase: UserUseCase) : NewUserMVP.Presenter {
    @Throws(DniValidationException::class)
    override fun newUser(user: User) {
        if (!UserValidation.validateDni(user.dni))
            throw DniValidationException()

        view.notifyUserSaved(userUseCase.newUser(user))
    }
}