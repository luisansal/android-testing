package com.luisansal.jetpack.data.datastore

import com.luisansal.jetpack.data.Result
import com.luisansal.jetpack.data.network.request.MessageRequest
import com.luisansal.jetpack.domain.network.ApiService
import com.luisansal.jetpack.utils.ErrorUtil
import java.lang.Exception

class MapsCloudStore(private val apiService: ApiService) {

    suspend fun sendPosition(message: String, latiude: Double, longitude: Double): Result<Boolean> {
        try {
            val messageRequest = MessageRequest(message)
            val response = apiService.sendPosition(messageRequest, latiude, longitude)

            if (response.isSuccessful) {

                return Result.Success((response.body()?.statusCode == 200))
            }
            return Result.Error(ErrorUtil.handle(response.errorBody()))
        } catch (e: Exception) {
            return Result.Error(ErrorUtil.handle(e))
        }
    }

}
