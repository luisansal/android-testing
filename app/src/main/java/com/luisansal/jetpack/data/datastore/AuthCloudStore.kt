package com.luisansal.jetpack.data.datastore

import com.luisansal.jetpack.data.Result
import com.luisansal.jetpack.data.mappers.UserResponseMapper
import com.luisansal.jetpack.data.network.request.LoginRequest
import com.luisansal.jetpack.data.preferences.AuthSharedPreferences
import com.luisansal.jetpack.data.preferences.UserSharedPreferences
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.domain.network.ApiService
import com.luisansal.jetpack.utils.apiService
import java.util.*

class AuthCloudStore(
    private val apiService: ApiService, private val authSharedPreferences: AuthSharedPreferences,
    private val userSharedPreferences: UserSharedPreferences
) {

    suspend fun login(email: String, password: String): Result<User> {
        return apiService {
            val loginRequest = LoginRequest(email, password)
            val response = apiService.login(loginRequest)
            val body = response.body()
            result(response) {
                val user = UserResponseMapper().map(body!!.user)
                user.also {
                    authSharedPreferences.isLogged = true
                    authSharedPreferences.token = body.accessToken
                    authSharedPreferences.tokenType = body.tokenType
                    authSharedPreferences.tokenExpires = Calendar.getInstance().timeInMillis + body.expiresIn
                    userSharedPreferences.user = user
                }
            }
        }
    }

    suspend fun logout(): Result<Boolean> {
        return apiService {
            val logoutResponse = apiService.logout()
            val body = logoutResponse.body()
            result(logoutResponse) {
                userSharedPreferences.clear()
                authSharedPreferences.clear()
                body?.statusCode == 200
            }
        }
    }
}