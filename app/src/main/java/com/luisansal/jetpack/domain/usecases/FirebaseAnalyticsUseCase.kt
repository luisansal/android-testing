package com.luisansal.jetpack.domain.usecases

import com.luisansal.jetpack.utils.hardware.HardwareInfoRetriever
import com.luisansal.jetpack.domain.analytics.EventoModel
import com.luisansal.jetpack.domain.analytics.PantallaModel
import com.luisansal.jetpack.domain.analytics.TagAnalyticsHelper
import com.luisansal.jetpack.domain.analytics.TagAnalyticsHelper.construirEvento2
import com.luisansal.jetpack.domain.analytics.TagAnalyticsHelper.construirEventoPorId
import com.luisansal.jetpack.domain.analytics.TagAnalyticsHelper.construirEventoPorRol
import com.luisansal.jetpack.domain.analytics.TagAnalyticsHelper.construirNombrePantalla
import com.luisansal.jetpack.data.repository.SesionDataRepository
import com.luisansal.jetpack.domain.entity.Rol
import com.luisansal.jetpack.domain.repository.FirebaseAnalyticsRepository
import com.luisansal.jetpack.data.repository.UserRepository
import com.luisansal.jetpack.domain.analytics.TagAnalytics
import com.luisansal.jetpack.domain.logs.LogRepository

class FirebaseAnalyticsUseCase(private val sesionDataRepository: SesionDataRepository,
                               private val userRepository: UserRepository,
                               private val hardwareInfoRetriever: HardwareInfoRetriever,
                               private val analyticsRepository: FirebaseAnalyticsRepository,
                               private val logRepository: LogRepository
) {

    fun enviarPantallRDD(requestRdd: RequestPantallaRdd, rol: Rol): String {

        val sesion = requireNotNull(sesionDataRepository.obtener())
        val pantalla = construirNombrePantalla(requestRdd.tagAnalytics, rol)
        val hardwareInfo = hardwareInfoRetriever.get()

        pantalla?.let {
            val pantallaModel = PantallaModel(
                    sesion = sesion,
                    estadoConexion = hardwareInfo.currentNetworkStatus,
                    ambiente = hardwareInfo.buildVariant,
                    screenName = it)

            analyticsRepository.enviarPantalla(pantallaModel)
            logRepository.guardarPantalla(pantallaModel)
        }
        return pantalla ?: "No se pudo crear Tag"
    }

    fun enviarPantallPerfil(requestPerfil: RequestPantallaPerfil): String {

        val sesion = requireNotNull(sesionDataRepository.obtener())
        val rolPersona = requestPerfil.rol
        val pantalla = construirNombrePantalla(requestPerfil.tagAnalytics, rolPersona)
        val hardwareInfo = hardwareInfoRetriever.get()

        pantalla?.let {
            val pantallaModel = PantallaModel(
                    sesion = sesion,
                    estadoConexion = hardwareInfo.currentNetworkStatus,
                    ambiente = hardwareInfo.buildVariant,
                    screenName = it)

            analyticsRepository.enviarPantalla(pantallaModel)
            logRepository.guardarPantalla(pantallaModel)
        }
        return pantalla ?: "No se pudo crear Tag"
    }

    fun enviarEvento(request: RequestEvento): EventoModel {

        val evento = TagAnalyticsHelper.construirEvento(request.tagAnalytics)

        analyticsRepository.enviarEvento(evento)
        logRepository.guardarEvento(evento)
        return evento
    }

    fun enviarEvento2(request: RequestEvento2): EventoModel {
        val evento = construirEvento2(request.tagAnalytics, request.nombreBoton, request.nombreDocumento)
        analyticsRepository.enviarEvento(evento)
        return evento
    }

    fun enviarEventoPorRol(request: RequestEventoPorRol): EventoModel {
        val sesion = checkNotNull(sesionDataRepository.obtener())
        val evento = construirEventoPorRol(request.tagAnalytics, sesion, request.rol)

        analyticsRepository.enviarEvento(evento)
        logRepository.guardarEvento(evento)
        return evento
    }

    fun enviarEventoPersonaId(request: RequestEventoPorId): EventoModel {
        val evento = construirEventoPorId(request.personaId)
        analyticsRepository.enviarEvento(evento)
        logRepository.guardarEvento(evento)
        return evento
    }

    class RequestEvento(val tagAnalytics: TagAnalytics)

    class RequestEvento2(val tagAnalytics: TagAnalytics,
                         val nombreBoton: String,
                         val nombreDocumento: String)

    class RequestEventoPorId(val tagAnalytics: TagAnalytics,
                             val personaId: Long)

    class RequestEventoPorRol(val tagAnalytics: TagAnalytics,
                              val rol: Rol)

    class RequestPantallaRdd(val tagAnalytics: TagAnalytics,
                             val planId: Long)

    class RequestPantallaPerfil(val tagAnalytics: TagAnalytics,
                                val rol: Rol)
}