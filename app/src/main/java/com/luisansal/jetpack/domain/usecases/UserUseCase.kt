package com.luisansal.jetpack.domain.usecases

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.luisansal.jetpack.data.repository.UserRepository
import com.luisansal.jetpack.domain.entity.User

class UserUseCase(private val userRepository: UserRepository) {

    fun newUser(user: User): User {
        userRepository.save(user)
        return user
    }

    fun getUser(dni: String): User? {
        return userRepository.getUserByDni(dni)
    }


    fun validateDuplicatedUser(dni: String): Boolean {
        return userRepository.getUserByDni(dni) !== null
    }

    fun getAllUser(): List<User> {
        return userRepository.allUsers
    }

    fun getAllUserPaged(): LiveData<PagedList<User>> {
        return LivePagedListBuilder(userRepository.allUsersPaging, 50).build()
    }

    fun getUserById(id: Long): User? {
        return userRepository.getUserById(id)
    }
}