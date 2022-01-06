package com.luisansal.jetpack.core.utils

import com.google.gson.Gson
import com.luisansal.jetpack.core.data.network.response.StatusResponse
import com.luisansal.jetpack.core.domain.exceptions.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import com.luisansal.jetpack.core.data.Result

object ErrorUtil {

    fun <T : Any> result(response: Response<*>): Result<T> {
        return Result.Error(handle(response.errorBody(), response.code()))
    }

    fun <T : Any> result(e: Throwable): Result<T> {
        return Result.Error(handle(e))
    }


    private fun handle(responseBody: ResponseBody?, statusCode: Int): Exception {
        val bodyJson = responseBody?.string()
        val errorResponse = Gson().fromJson(bodyJson, StatusResponse::class.java)

        return when (statusCode) {
            401 -> {
                UnauthorizedException(errorResponse.message)
            }
            403 -> {
                RequestResouseForbiddenException(errorResponse.message)
            }
            404 -> {
                NotFoundException(errorResponse.message)
            }
            500, 400 -> {
                ServiceErrorException(errorResponse.message)
            }
            0 -> {
                UnexpectedException("Response es diferente a la definida en la solicitud: $bodyJson")
            }
            else -> {
                UnexpectedException(errorResponse.message)
            }
        }
    }

    private fun handle(e: Throwable): Exception = when (e) {
        is SocketTimeoutException -> {
            com.luisansal.jetpack.core.domain.exceptions.SocketTimeoutException()
        }
        is ConnectException -> {
            com.luisansal.jetpack.core.domain.exceptions.ConnectException()
        }
        is HttpException -> {
            HttpException()
        }
        is UnknownHostException -> {
            com.luisansal.jetpack.core.domain.exceptions.UnknownHostException()
        }
        else -> {
            e as java.lang.Exception
        }
    }
}

suspend fun <T : Any> apiService(invoker: suspend Result<T>.() -> Result<T>): Result<T> {
    return try {
        withContext(Dispatchers.IO) {
            invoker(Result.Success(null))
        }
    } catch (e: Throwable) {
        ErrorUtil.result(e)
    }
}