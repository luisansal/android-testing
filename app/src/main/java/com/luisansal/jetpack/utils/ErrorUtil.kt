package com.luisansal.jetpack.utils

import com.google.gson.Gson
import com.luisansal.jetpack.data.network.response.StatusResponse
import com.luisansal.jetpack.domain.exceptions.RequestResouseForbiddenException
import com.luisansal.jetpack.domain.exceptions.UnExpectedException
import com.luisansal.jetpack.domain.exceptions.UnauthorizedException
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import com.luisansal.jetpack.data.Result

object ErrorUtil {

    fun <T : Any> result(response: Response<*>): Result<T> {
        return Result.Error(handle(response.errorBody()))
    }

    fun <T : Any> result(e: Throwable): Result<T> {
        return Result.Error(handle(e))
    }

    private fun handle(responseBody: ResponseBody?): Exception {
        val bodyJson = responseBody?.string()
        val errorResponse = Gson().fromJson(bodyJson, StatusResponse::class.java)

        return when (errorResponse.statusCode) {
            403 -> {
                RequestResouseForbiddenException(errorResponse.error)
            }
            401 -> {
                UnauthorizedException(errorResponse.error)
            }
            0 -> {
                UnExpectedException("Response es diferente a la definida en la solicitud: $bodyJson")
            }
            else -> {
                UnExpectedException(errorResponse.error)
            }
        }
    }

    private fun handle(e: Throwable): Exception = when (e) {
        is SocketTimeoutException -> {
            com.luisansal.jetpack.domain.exceptions.SocketTimeoutException()
        }
        is ConnectException -> {
            com.luisansal.jetpack.domain.exceptions.ConnectException()
        }
        is HttpException -> {
            com.luisansal.jetpack.domain.exceptions.HttpException()
        }
        else -> {
            e as java.lang.Exception
        }
    }
}