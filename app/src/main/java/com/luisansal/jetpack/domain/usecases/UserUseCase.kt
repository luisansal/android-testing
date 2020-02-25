package com.luisansal.jetpack.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.luisansal.jetpack.data.repository.UserRepository
import com.luisansal.jetpack.domain.entity.User

class UserUseCase(private val userRepository: UserRepository) {

    fun newUser(user: User): LiveData<User> {
        val mutableLiveData = MutableLiveData<User>()
        userRepository.save(user)
        mutableLiveData.postValue(user)
        return mutableLiveData
    }

    fun getUser(dni: String): LiveData<User?> {

        return userRepository.getUserByDni(dni)
    }

    fun getAllUser(): LiveData<List<User>> {
        return userRepository.allUsers
    }

    fun getUserById(id: Long): LiveData<User?> {
        return userRepository.getUserById(id)
    }
}