package com.luisansal.jetpack.features.manageusers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.luisansal.jetpack.data.repository.UserRepository
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.domain.usecases.UserUseCase
import com.luisansal.jetpack.ui.features.manageusers.newuser.NewUserMVP
import com.luisansal.jetpack.ui.features.manageusers.newuser.NewUserPresenter
import io.mockk.*
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

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
        every { user.dni } returns "1525896"
        every { user.name } returns "Pepito"
        every { user.lastName } returns "Rodriguez"
        return user
    }

    @Test
    fun `nuevo usuario`() {
        val mutableUserId = MutableLiveData<User>()
        val user = getMockedUser()
        mutableUserId.postValue(user)

        every { userUseCase.newUser(user) } returns mutableUserId
        every { userUseCase.getUser(user.dni) } returns mutableUserId

        newUserPresenter.newUser(user)

        verify {
            userUseCase.newUser(user)
//            userUseCase.getUser(user.dni)
        }

    }


}