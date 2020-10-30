package com.luisansal.jetpack.domain.network

import com.luisansal.jetpack.data.network.request.LoginRequest
import com.luisansal.jetpack.data.network.response.LoginResponse
import com.luisansal.jetpack.data.network.response.StatusResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    companion object{
        val BASE_URL = "http://192.168.8.131:8080"
    }

    @POST("/api/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse?>

    @POST("/api/logout")
    suspend fun logout() : StatusResponse
}