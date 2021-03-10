package com.luisansal.jetpack.data.datastore

import com.luisansal.jetpack.data.mappers.UserResponseMapper
import com.luisansal.jetpack.domain.network.ApiService
import com.luisansal.jetpack.data.network.request.LoginRequest
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.data.Result
import com.luisansal.jetpack.data.preferences.AuthSharedPreferences
import com.luisansal.jetpack.data.preferences.UserSharedPreferences
import com.luisansal.jetpack.utils.ErrorUtil
import java.util.*

class AuthCloudStore(
    private val apiService: ApiService, private val authSharedPreferences: AuthSharedPreferences,
    private val userSharedPreferences: UserSharedPreferences
) {

    suspend fun login(email: String, password: String): Result<User> {
        try {
            val loginRequest = LoginRequest(email, password)
            val response = apiService.login(loginRequest)
            if (response.isSuccessful) {
                val body = response.body()
                val user = UserResponseMapper().map(body!!.user)

                authSharedPreferences.logged = true
                authSharedPreferences.token = body.accessToken
                authSharedPreferences.tokenType = body.tokenType
                authSharedPreferences.tokenExpires = Calendar.getInstance().timeInMillis + body.expiresIn
                userSharedPreferences.user = user

                return Result.Success(user)
            }
            return ErrorUtil.result(response)
        } catch (e: Throwable) {
            return ErrorUtil.result(e)
        }
    }

    suspend fun logout(): Result<Boolean> {
        try {
            val logoutResponse = apiService.logout()
            if (logoutResponse.isSuccessful) {
                userSharedPreferences.clear()
                authSharedPreferences.clear()
                return Result.Success((logoutResponse.body()?.statusCode == 200))
            }
            return ErrorUtil.result(logoutResponse)
        } catch (e: Throwable) {
            return ErrorUtil.result(e)
        }
    }
}