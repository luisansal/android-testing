package com.luisansal.jetpack.domain.network

import com.luisansal.jetpack.BuildConfig
import com.luisansal.jetpack.data.network.request.LoginRequest
import com.luisansal.jetpack.data.network.request.MessageRequest
import com.luisansal.jetpack.data.network.response.LoginResponse
import com.luisansal.jetpack.data.network.response.StatusResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    companion object {
        const val PUSHER_API_CLUSTER = BuildConfig.PUSHER_API_CLUSTER
        const val PUSHER_API_KEY = BuildConfig.PUSHER_API_KEY
        const val BASE_URL: String = BuildConfig.BASE_HOST
        const val BROADCAST_URL = "${BASE_URL}/broadcasting/auth"
    }

    @POST("/api/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/api/logout")
    suspend fun logout(): Response<StatusResponse>

    @POST("/api/send-message")
    suspend fun sendMessage(@Body messageRequest: MessageRequest): Response<StatusResponse>

    @POST("/api/send-position/{latitude}/{longitude}")
    suspend fun sendPosition(
            @Body messageRequest: MessageRequest,
            @Path("latitude") latitude: Double,
            @Path("longitude") longitude : Double): Response<StatusResponse>
}