package com.luisansal.jetpack.data

import com.luisansal.jetpack.domain.exceptions.UnexpectedException
import com.luisansal.jetpack.utils.ErrorUtil
import retrofit2.Response

sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T?) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data class Errors(val exceptions: List<Exception>) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            is Errors -> "Errors[exception=$exceptions]"
        }
    }

    inline fun <reified T : Any> success(invoker: () -> T?): Result<T> {
        return Success(invoker())
    }

    fun error(invoker: () -> Response<*>): Result<T> {
        return ErrorUtil.result(invoker())
    }

    fun error(e: Exception): Result<T> {
        return ErrorUtil.result(e)
    }

    inline fun <reified T : Any> result(response: Response<*>, bodyCode: Int? = 0, exception: Exception = UnexpectedException(), success: () -> T?): Result<T> {
        return if (response.isSuccessful) {
            if (bodyCode != 0) {
                ErrorUtil.result(exception)
            } else {
                Success(success())
            }
        } else {
            ErrorUtil.result(response)
        }
    }
}