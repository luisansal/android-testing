package com.luisansal.jetpack.domain.exception

open class AnalyticsException(mensaje: String = "No se pudo crear tag") :
        Exception(mensaje)

class AnalyticsEventException(mensaje: String = "No se pudo crear tag de evento") :
        AnalyticsException(mensaje)