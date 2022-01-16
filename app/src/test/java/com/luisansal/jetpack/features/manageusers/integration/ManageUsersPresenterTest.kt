package com.luisansal.jetpack.features.manageusers.integration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.luisansal.jetpack.base.BaseIntegrationTest
import com.luisansal.jetpack.core.domain.entity.User
import com.luisansal.jetpack.domain.usecases.UserUseCase
import io.mockk.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.inject

class ManageUsersPresenterTest : BaseIntegrationTest(){

    @get:Rule
    val rule = InstantTaskExecutorRule()

    lateinit var newUserPresenter: NewUserPresenter
    val mView: NewUserMVP.View = mockk(relaxed = true)
    val userUseCase: UserUseCase by inject()

    @Before
    fun before() {
        newUserPresenter = NewUserPresenter(mView, userUseCase)
    }

    fun getMockedUser(): User {
        val user: User = mockkClass(User::class)
        every { user.id } returns 1
        every { user.dni } returns "15258965"
        every { user.name } returns "Pepito"
        every { user.lastName } returns "Rodriguez"
        return user
    }

    @Test
    fun `nuevo usuario`() {
        val user = getMockedUser()

        newUserPresenter.newUser(user)
        waitUiThread()
        verify {
            mView.notifyUserSaved(any())
        }

    }


}