package com.luisansal.jetpack.data.datastore

import com.luisansal.jetpack.data.mappers.UserResponseMapper
import com.luisansal.jetpack.domain.network.ApiService
import com.luisansal.jetpack.data.network.request.LoginRequest
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.data.Result
import com.luisansal.jetpack.data.preferences.AuthSharedPreferences
import com.luisansal.jetpack.data.preferences.UserSharedPreferences
import com.luisansal.jetpack.domain.exception.LoginBadCredentialsException
import java.io.IOException
import java.util.*

class AuthCloudStore(private val apiService: ApiService, private val authSharedPreferences: AuthSharedPreferences,
                     private val userSharedPreferences: UserSharedPreferences) {

    suspend fun login(email: String, password: String): Result<User> {
        try {
            val loginRequest = LoginRequest(email, password)
            val response = apiService.login(loginRequest).body() ?: throw LoginBadCredentialsException(email)

            val user = UserResponseMapper().map(response.user)

            authSharedPreferences.logged = true
            authSharedPreferences.token = response.accessToken
            authSharedPreferences.tokenType = response.tokenType
            authSharedPreferences.tokenExpires = Calendar.getInstance().timeInMillis + (response?.expiresIn ?: 0)
            userSharedPreferences.user = user

            return Result.Success(user)
        } catch (e: Throwable) {
            return Result.Error(e as Exception)
        }
    }

    suspend fun logout(): Result<Boolean> {
        try {
            val logoutResponse = apiService.logout()
            userSharedPreferences.clear()
            authSharedPreferences.clear()
            return Result.Success((logoutResponse.statusCode == 200))
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }
}