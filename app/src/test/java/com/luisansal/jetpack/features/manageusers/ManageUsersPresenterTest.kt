package com.luisansal.jetpack.features.manageusers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.domain.usecases.UserUseCase
import com.luisansal.jetpack.ui.features.manageusers.newuser.NewUserMVP
import com.luisansal.jetpack.ui.features.manageusers.newuser.NewUserPresenter
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ManageUsersPresenterTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    lateinit var newUserPresenter: NewUserPresenter
    val mView: NewUserMVP.View = mockk(relaxed = true)
    val userUseCase: UserUseCase = mockk()

    @Before
    fun before() {
        newUserPresenter = NewUserPresenter(mView, userUseCase)
    }

    fun getMockedUser(): User {
        val user: User = mockkClass(User::class)
        every { user.id } returns 1
        every { user.dni } returns "15258968"
        every { user.name } returns "Pepito"
        every { user.lastName } returns "Rodriguez"
        return user
    }

    @Test
    fun `nuevo usuario`() {

        val user = getMockedUser()

        every { userUseCase.newUser(user) } returns user

        newUserPresenter.newUser(user)

        verify {
            mView.notifyUserSaved(any())
        }

    }


}