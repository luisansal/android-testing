package com.luisansal.jetpack.ui.features.analytics

import com.luisansal.jetpack.domain.analytics.TagAnalytics
import com.luisansal.jetpack.domain.user.Rol
import com.luisansal.jetpack.domain.usecases.FirebaseAnalyticsUseCase

class FirebaseAnalyticsPresenter(private val enviarVistaPantalla: FirebaseAnalyticsUseCase) {

    suspend fun enviarPantallaRdd(tagAnalytics: TagAnalytics, planId: Long) {
        val request = FirebaseAnalyticsUseCase.RequestPantallaRdd(tagAnalytics, planId)
        enviarVistaPantalla.enviarPantallRDD(request,Rol.WRITER)
    }

    suspend fun enviarPantallaMiMapa(tagAnalytics: TagAnalytics, planId: Long) {
        val request = FirebaseAnalyticsUseCase.RequestPantallaRdd(tagAnalytics, planId)
        enviarVistaPantalla.enviarPantallRDD(request,Rol.WRITER)
    }

    suspend fun enviarPantallaPerfil(tagAnalytics: TagAnalytics, rol: Rol) {
        val request = FirebaseAnalyticsUseCase.RequestPantallaPerfil(tagAnalytics, rol)
        enviarVistaPantalla.enviarPantallPerfil(request)
    }

    fun enviarEvento(tagAnalytics: TagAnalytics) {
        val request = FirebaseAnalyticsUseCase.RequestEvento(tagAnalytics)
        enviarVistaPantalla.enviarEvento(request)
    }

    fun enviarEventoMarcador(tagAnalytics: TagAnalytics, personaId: Long){
        val request = FirebaseAnalyticsUseCase.RequestEventoPorId(tagAnalytics, personaId)
        enviarVistaPantalla.enviarEventoPersonaId(request)
    }

//    private class EventoObserver : BaseSingleObserver<String>() {
//        override fun onError(e: Throwable) {
//            Log.e("TAG_ANALYTICS", e.localizedMessage)
//        }
//
//        override fun onSuccess(t: String) {
//            Log.d("TAG_ANALYTICS", t)
//        }
//    }
//
//    private class EventoModelObserver : BaseSingleObserver<EventoModel>() {
//        override fun onError(e: Throwable) {
//            Log.e("TAG_ANALYTICS", e.localizedMessage)
//        }
//
//        override fun onSuccess(t: EventoModel) {
//            Log.d("TAG_ANALYTICS", t.action)
//        }
//
//    }
}