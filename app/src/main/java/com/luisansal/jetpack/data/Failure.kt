package com.luisansal.jetpack.data

import androidx.annotation.StringRes
import com.luisansal.jetpack.R

sealed class Failure(val messageRes: Int = R.string.server_error) {
    data class NetworkConnection(@StringRes var message: Int = R.string.internet_error_connection) :
        Failure(message)

    data class Http(@StringRes var message: Int = R.string.server_error) : Failure(message)
    object UnExpected : Failure()
    class TokenInvalidOrExpired(messageRes: Int = R.string.authentication_session_expired) : Failure(messageRes)
    class UserUnauthorizedException(messageRes: Int = R.string.authentication_session_was_closed) : Failure(messageRes)
    object UserExistException : Failure()
    object UserNotFoundException : Failure()
    object Canceled : Failure()
}