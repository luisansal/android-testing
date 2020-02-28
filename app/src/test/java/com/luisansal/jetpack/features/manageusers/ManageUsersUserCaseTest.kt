package com.luisansal.jetpack.features.manageusers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.luisansal.jetpack.data.repository.UserRepository
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.domain.usecases.UserUseCase
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldNotBe
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ManageUsersUserCaseTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    lateinit var userUseCase: UserUseCase
    val userRepository : UserRepository = mockk()

    @Before
    fun before() {
        userUseCase = UserUseCase(userRepository)

    }

    @Test
    fun `nuevo usuario`() {
        val user = getMockedUser()

        every { userRepository.save(user) } just Runs

        userUseCase.newUser(user)

        verify {
            userRepository.save(user)
        }
    }

    @Test
    fun `obtener usuario`() {
        val user = getMockedUser()
        val dni = user.dni

        every { userRepository.getUserByDni(dni) } returns user

        userUseCase.getUser(dni)

        verify {
            userRepository.getUserByDni(dni)
        }
    }

    @Test
    fun `validar usuario duplicado`() {
        val user = getMockedUser()
        val dni = user.dni

        every { userRepository.getUserByDni(any()) } returns null

        userUseCase.validateDuplicatedUser(dni) shouldNotBe true
    }

    @Test
    fun `lista de usuarios`() = runBlocking {
        val users = UsersMockDataHelper().getUsers()

        every { userRepository.allUsers } returns users

        userUseCase.getAllUser()

        coVerify {
            userRepository.allUsers
        }
    }

    @Test
    fun `eliminar usuario`() {
        val dni = "12345678"

        every { userRepository.deleteUser(any()) } returns true

        userUseCase.deleUser(dni)

        verify {
            userRepository.deleteUser(any())
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