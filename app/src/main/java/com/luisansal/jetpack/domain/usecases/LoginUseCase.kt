package com.luisansal.jetpack.domain.usecases

import com.luisansal.jetpack.data.datastore.AuthCloudStore
import com.luisansal.jetpack.data.Result
import com.luisansal.jetpack.domain.entity.User

class LoginUseCase(val authCloudStore: AuthCloudStore) {

    // in-memory cache of the loggedInUser object
    var user: User? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    suspend fun logout() {
        user = null
        authCloudStore.logout()
    }

    suspend fun login(username: String, password: String): Result<User> {
        // handle login
        val result = authCloudStore.login(username, password)
        if (result is Result.Success) {
            result.data?.let { setLoggedInUser(it) }
        }

        return result
    }

    private fun setLoggedInUser(user: User) {
        this.user = user
    }
}