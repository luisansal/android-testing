package com.luisansal.jetpack.features.manageusers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.luisansal.jetpack.data.repository.UserRepository
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.domain.usecases.UserUseCase
import io.mockk.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ManageUsersUserCaseTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    lateinit var userUseCase: UserUseCase
    val userRepository : UserRepository = mockk()
    val observer : Observer<User> = mockk(relaxed = true)
    val observerGet : Observer<User?> = mockk(relaxed = true)
    val observerAll : Observer<List<User>> = mockk(relaxed = true)

    @Before
    fun before() {
        userUseCase = UserUseCase(userRepository)

    }

    @Test
    fun `nuevo usuario`() {
        val user = getMockedUser()

        every { userRepository.save(user) } just Runs

        userUseCase.newUser(user).observeForever(observer)

        verify {
            observer.onChanged(user)
        }
    }


    @Test
    fun `get usuario`() {
//        val mutableUser = MutableLiveData<User>()
//        val user = getMockedUser()
//        mutableUser.postValue(user)
//
//        val dni = user.dni
//
//        every { userRepository.allUsers } returns mutableUser
//
//        userUseCase.getUser(dni).observeForever(observerGet)
//
//        verify {
//            observerGet.onChanged(user)
//        }
    }

    @Test
    fun `lista de usuarios`() {
        val mutableUser = MutableLiveData<User>()
        val user = getMockedUser()
        mutableUser.postValue(user)

        val dni = user.dni

        every { userRepository.getUserByDni(dni) } returns mutableUser

//        userUseCase.getAllUser().observeForever(observerGet)

        verify {
            observerGet.onChanged(user)
        }
    }

    fun getMockedUser(): User {
        val user: User = mockkClass(User::class)
        every { user.id } returns 1
        every { user.dni } returns "1525896"
        every { user.name } returns "Pepito"
        every { user.lastName } returns "Rodriguez"
        return user
    }


}