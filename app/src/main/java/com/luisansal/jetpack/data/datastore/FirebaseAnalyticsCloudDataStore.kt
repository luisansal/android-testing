package com.luisansal.jetpack.data.datastore

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.luisansal.jetpack.BuildConfig
import com.luisansal.jetpack.utils.hardware.BuildVariant
import com.luisansal.jetpack.utils.hardware.NetworkStatus
import com.luisansal.jetpack.domain.analytics.EventoModel
import com.luisansal.jetpack.domain.logs.LogTag.ACTION
import com.luisansal.jetpack.domain.logs.LogTag.AMBIENTE
import com.luisansal.jetpack.domain.logs.LogTag.CATEGORIA
import com.luisansal.jetpack.domain.logs.LogTag.ESTADO_CONEXION
import com.luisansal.jetpack.domain.logs.LogTag.LABEL
import com.luisansal.jetpack.domain.logs.LogTag.LOG_SCREEN_VIEW
import com.luisansal.jetpack.domain.logs.LogTag.LOG_VIRTUAL_EVENT
import com.luisansal.jetpack.domain.logs.LogTag.PPR_CODIGO
import com.luisansal.jetpack.domain.logs.LogTag.PRD_CODFIGO
import com.luisansal.jetpack.domain.logs.LogTag.QAS_CODIGO
import com.luisansal.jetpack.domain.logs.LogTag.ROL
import com.luisansal.jetpack.domain.logs.LogTag.SCREEN_NAME
import com.luisansal.jetpack.domain.analytics.PantallaModel

class FirebaseAnalyticsCloudDataStore(val context: Context) {

    private val tipoAmbiente = BuildConfig.BUILD_TYPE.ambiente()

    private val firebaseClient: FirebaseAnalytics by lazy { FirebaseAnalytics.getInstance(context) }

    private fun String.ambiente(): String {
        return when (this) {
            QAS_CODIGO -> "QAs"
            PPR_CODIGO -> "QAs" // no definieron en ppr - se asume Qas
            PRD_CODFIGO -> "Produccion"
            else -> ""
        }
    }

    fun enviarPantalla(pantallaModel: PantallaModel) {
        val params = Bundle()
        params.insertarDatosPantalla(pantallaModel.screenName)
        firebaseClient.aplicarUserProperties(pantallaModel)
        firebaseClient.logEvent(LOG_SCREEN_VIEW, params)
    }

    private fun Bundle.insertarDatosPantalla(nombrePantalla: String) {
        this.apply {
            putString(SCREEN_NAME, nombrePantalla)
            putString(AMBIENTE, tipoAmbiente)
        }
    }

    fun enviarEvento(eventoModel: EventoModel): EventoModel {
        val params = Bundle()
        params.insertarDatosEvento(eventoModel)
        firebaseClient.logEvent(LOG_VIRTUAL_EVENT, params)
        return eventoModel
    }

    private fun Bundle.insertarDatosEvento(eventoModel: EventoModel) {
        this.apply {
            putString(CATEGORIA, eventoModel.category)
            putString(ACTION, eventoModel.action)
            putString(LABEL, eventoModel.label)
            putString(SCREEN_NAME, eventoModel.screenName)
            putString(AMBIENTE, tipoAmbiente)
        }
    }

    private fun FirebaseAnalytics.aplicarUserProperties(pantallaModel: PantallaModel) {
        setUserProperty(ROL, pantallaModel.sesion.codigoRol)
        setUserProperty(AMBIENTE, mapVariant(pantallaModel.ambiente))
        setUserProperty(ESTADO_CONEXION, mapNetworkStatus(pantallaModel.estadoConexion))
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

}