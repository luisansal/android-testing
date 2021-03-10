package com.luisansal.jetpack.data.datastore

import com.luisansal.jetpack.data.network.request.MessageRequest
import com.luisansal.jetpack.data.network.response.StatusResponse
import com.luisansal.jetpack.domain.network.ApiService
import com.luisansal.jetpack.data.Result
import com.luisansal.jetpack.utils.ErrorUtil

class ChatCloudStore(private val apiService: ApiService) {
    suspend fun sendMessage(message: String): Result<StatusResponse> {
        try {
            val messageRequest = MessageRequest(message)
            val response = apiService.sendMessage(messageRequest)
            if(response.isSuccessful){

                return Result.Success(response.body()!!)
            }
            return ErrorUtil.result(response)
        } catch (e: Exception) {
            return ErrorUtil.result(e)
        }
    }
}