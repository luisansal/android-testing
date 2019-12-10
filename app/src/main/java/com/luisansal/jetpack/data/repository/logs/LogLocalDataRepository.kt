package com.luisansal.jetpack.data.repository.logs

import com.google.gson.Gson
import com.luisansal.jetpack.BuildConfig
import com.luisansal.jetpack.common.hardware.BuildVariant
import com.luisansal.jetpack.common.hardware.NetworkStatus
import com.luisansal.jetpack.domain.analytics.EventoModel
import com.luisansal.jetpack.domain.logs.LogTag
import com.luisansal.jetpack.domain.analytics.PantallaModel
import com.luisansal.jetpack.domain.logs.Evento
import com.luisansal.jetpack.domain.logs.LogRepository
import com.luisansal.jetpack.domain.logs.Pantalla


class LogLocalDataRepository(private val escribirArchivoLocalDataStore: EscribirArchivoLocalDataStore) : LogRepository {


    val TYPE_OPEN_SCREEN = "openScreen"
    val TYPE_EVENT = "virtualEvent"

    val gson = Gson()
    private val tipoAmbiente = BuildConfig.BUILD_TYPE.ambiente()

    override fun guardarEvento(eventoModel: EventoModel): EventoModel {
        val evento = obtenerEventoAGuardar(eventoModel)
        escribirArchivoLocalDataStore.guardarLog(TYPE_EVENT, gson.toJson(evento))
        return eventoModel
    }

    override fun guardarPantalla(pantallaModel: PantallaModel) {
        val pantalla = obtenerPantallaAGuardar(pantallaModel)
        escribirArchivoLocalDataStore.guardarLog(TYPE_OPEN_SCREEN, gson.toJson(pantalla))
    }

    private fun String.ambiente(): String {
        return when (this) {
            LogTag.QAS_CODIGO -> "QAs"
            LogTag.PPR_CODIGO -> "QAs" // no definieron en ppr - se asume Qas
            LogTag.PRD_CODFIGO -> "Produccion"
            else -> ""
        }
    }

    private fun mapVariant(buildVariant: BuildVariant): String {
        return when (buildVariant) {
            BuildVariant.QAS -> "QAs"
            BuildVariant.PPR -> "QAs" // no definieron en ppr - se asume Qas
            BuildVariant.PRD -> "Produccion"
        }
    }

    private fun mapNetworkStatus(networkStatus: NetworkStatus): String {
        return when (networkStatus) {
            NetworkStatus.CONNECTED -> "online"
            NetworkStatus.DISCONNECTED -> "offline"
        }
    }

    private fun obtenerEventoAGuardar(eventoModel: EventoModel): Evento {
        return Evento(
                categoria = eventoModel.category,
                action = eventoModel.action,
                label = eventoModel.label,
                screenName = eventoModel.screenName,
                ambiente = tipoAmbiente)
    }

    private fun obtenerPantallaAGuardar(pantallaModel: PantallaModel): Pantalla {
        return Pantalla(
                rol = pantallaModel.sesion.codigoRol,
                estadoConexion = mapNetworkStatus(pantallaModel.estadoConexion),
                ambiente = mapVariant(pantallaModel.ambiente),
                screenName = pantallaModel.screenName)
    }
}