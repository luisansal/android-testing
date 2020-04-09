package com.luisansal.jetpack.domain.usecases

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.luisansal.jetpack.data.repository.UserRepository
import com.luisansal.jetpack.domain.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    suspend fun getAllUser(): List<User> = withContext(Dispatchers.Default) {
         userRepository.allUsers
    }

    suspend fun getAllUserPaged(): LiveData<PagedList<User>> = withContext(Dispatchers.Default){
        LivePagedListBuilder(userRepository.allUsersPaging, 50).build()
    }

    fun getUserById(id: Long): User? {
        return userRepository.getUserById(id)
    }

    fun deleUser(dni: String): Boolean {
        return userRepository.deleteUser(dni)
    }

    fun deleUsers(): Boolean {
        return userRepository.deleteUsers()
    }
}