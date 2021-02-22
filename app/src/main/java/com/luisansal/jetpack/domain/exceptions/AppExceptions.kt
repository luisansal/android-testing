package com.luisansal.jetpack.domain.exceptions

import com.luisansal.jetpack.domain.entity.User

class ConnectException : Exception()
class CreateUserValidationException : Exception()
class DniValidationException : Exception()
class HttpException : Exception()
open class RequestDeniedException(message: String = RequestDeniedException::class.simpleName ?: "") : Exception(message)
class RequestPlacesDeniedException(message: String = RequestPlacesDeniedException::class.simpleName ?: "") : RequestDeniedException(message)
class RequestDirectionsDeniedException(message: String = RequestDirectionsDeniedException::class.simpleName ?: "") : RequestDeniedException(message)
class RequestResouseForbiddenException(message: String = "") : Exception(message)
class SocketTimeoutException(message: String = "No hay respuesta en la conexión") : Exception(message)
class UnauthorizedException(message: String = "") : Exception(message)
class UnExpectedException(message: String = "") : Exception(message)
class UserExistException(val user: User) : Exception()