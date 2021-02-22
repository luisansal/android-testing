package com.luisansal.jetpack.utils

import com.google.gson.Gson
import com.luisansal.jetpack.data.network.response.StatusResponse
import com.luisansal.jetpack.domain.exceptions.RequestResouseForbiddenException
import com.luisansal.jetpack.domain.exceptions.UnExpectedException
import com.luisansal.jetpack.domain.exceptions.UnauthorizedException
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

object ErrorUtil {

    fun handle(responseBody: ResponseBody?): Exception {
        val errorResponse = Gson().fromJson(responseBody?.string(), StatusResponse::class.java)

        return when (errorResponse.statusCode) {
            403 -> {
                RequestResouseForbiddenException(errorResponse.error)
            }
            401 -> {
                UnauthorizedException(errorResponse.error)
            }
            else -> {
                UnExpectedException(errorResponse.error)
            }
        }
    }

    fun handle(e: Throwable): Exception = when (e) {
        is SocketTimeoutException -> {
            com.luisansal.jetpack.domain.exceptions.SocketTimeoutException()
        }
        is ConnectException -> {
            com.luisansal.jetpack.domain.exceptions.ConnectException()
        }
        else -> {
            e as Exception
        }
    }

}