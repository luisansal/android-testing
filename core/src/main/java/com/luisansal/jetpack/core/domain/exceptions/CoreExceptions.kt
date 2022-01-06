package com.luisansal.jetpack.core.domain.exceptions

class ConnectException(message: String = "Conexión interrumpida") : Exception(message)
class UnknownHostException(message: String = "No hay conexión a internet") : Exception(message)
class SocketTimeoutException(message: String = "No hay respuesta en la conexión") : Exception(message)
class UnauthorizedException(message: String = "Usuario no autorizado") : Exception(message)
class UnexpectedException(message: String? = "Excepción no esperada") : Exception(message)
class HttpException(message: String = "Error en la petición http") : Exception(message)
class NotFoundException(message: String = "Not Found"):Exception(message)
class ServiceErrorException(message: String = "Error en el servicio"):Exception(message)
class StoreNotAvailableException(message: String = "Tienda no está disponible"):Exception(message)
class ErrorLogicServerException(message: String = "Error inesperado en el servicio"):Exception(message)
class CommerceCodeNotFoundException(message: String = "Tienda no tiene código de comercio"):Exception(message)
class TransactionCanceledException(message: String = "No se pudo realizar la transacción - Anular"):Exception(message)
class IncorrectPasswordException(message: String = "Contraseña incorrecta"):Exception(message)
class SaveSignatureException(message: String = "No se pudo guardar la firma"):Exception(message)
class UserNotExistException(message: String = "Usuario no existe"):Exception(message)
class PendingActivationException(message: String = "Usuario pendiente de activación"):Exception(message)
class InactiveUserException(message: String = "Usuario inactivo"):Exception(message)
class InvalidCodeException(message: String = "Codigo incorrecto"):Exception(message)
class EmailFormatException(message: String = "Email no tiene el formato adecuado"):Exception(message){
    companion object{ val code: Int = 20 }
}
class SendSMSException(message: String = "No se pudo enviar el SMS"):Exception(message)
class RequestResouseForbiddenException(message: String = "Request Resouse Forbidden Exception") : Exception(message)