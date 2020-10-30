package com.luisansal.jetpack.features.manageusers.newuser

import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.domain.exception.CreateUserValidationException
import com.luisansal.jetpack.domain.exception.DniValidationException
import com.luisansal.jetpack.domain.exception.UserExistException
import com.luisansal.jetpack.domain.usecases.LoginUseCase
import com.luisansal.jetpack.domain.usecases.UserUseCase
import com.luisansal.jetpack.features.manageusers.validation.UserValidation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewUserPresenter(
        private val view: NewUserMVP.View,
        private val userUseCase: UserUseCase,
        private val loginUseCase: LoginUseCase) : NewUserMVP.Presenter {

    @Throws(DniValidationException::class, UserExistException::class, CreateUserValidationException::class)
    override fun newUser(user: User) {

        if (!UserValidation.validateDni(user.dni))
            throw DniValidationException()

        val userExist = userUseCase.getUser(user.dni)
        if (userExist !== null)
            throw UserExistException(userExist)

        if (!UserValidation.validateUserToCreate(user))
            throw CreateUserValidationException()

        view.notifyUserSaved(userUseCase.newUser(user))
        view.nextStep(user)
    }

    override fun logout() {
        CoroutineScope(Dispatchers.Main).launch {
            loginUseCase.logout()
            view.afterLogout()
        }
    }

}