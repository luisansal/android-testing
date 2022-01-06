package com.luisansal.jetpack.domain.usecases

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.luisansal.jetpack.data.repository.UserRepository
import com.luisansal.jetpack.data.repository.VisitRepository
import com.luisansal.jetpack.core.domain.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserUseCase(private val userRepository: UserRepository, private val visitRepository: VisitRepository) {

    fun newUser(user: User): User {
        val userExist = userRepository.getUserByDni(user.dni)

        if(userExist !== null)
            return userExist

        val userId = userRepository.save(user)
        return userRepository.getUserById(userId)!!
    }

    fun getUser(dni: String): User? {
        return userRepository.getUserByDni(dni)
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

    suspend fun deleUsers(): Boolean = withContext(Dispatchers.Default){
        visitRepository.deleteAll()
        userRepository.deleteUsers()
    }
}