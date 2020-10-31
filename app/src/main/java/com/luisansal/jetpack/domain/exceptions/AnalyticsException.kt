package com.luisansal.jetpack.domain.exceptions

open class AnalyticsException(mensaje: String = "No se pudo crear tag") :
        Exception(mensaje)

class AnalyticsEventException(mensaje: String = "No se pudo crear tag de evento") :
        AnalyticsException(mensaje)