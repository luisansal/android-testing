package com.luisansal.jetpack.features.analytics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luisansal.jetpack.domain.analytics.TagAnalytics
import com.luisansal.jetpack.domain.entity.Rol
import com.luisansal.jetpack.domain.usecases.FirebaseAnalyticsUseCase
import java.lang.Exception

class FirebaseanalyticsViewModel(private val analytics: FirebaseAnalyticsUseCase) : ViewModel() {

    val fireBaseAnalyticsViewState = MutableLiveData<FirebaseanalyticsViewState>()

    suspend fun enviarPantallaRdd(tagAnalytics: TagAnalytics, planId: Long) {
        val request = FirebaseAnalyticsUseCase.RequestPantallaRdd(tagAnalytics, planId)
        analytics.enviarPantallRDD(request, Rol.WRITER)
    }

    suspend fun enviarPantallaMiMapa(tagAnalytics: TagAnalytics, planId: Long) {
        val request = FirebaseAnalyticsUseCase.RequestPantallaRdd(tagAnalytics, planId)
        analytics.enviarPantallRDD(request, Rol.WRITER)
    }

    suspend fun enviarPantallaPerfil(tagAnalytics: TagAnalytics, rol: Rol) {
        val request = FirebaseAnalyticsUseCase.RequestPantallaPerfil(tagAnalytics, rol)
        analytics.enviarPantallPerfil(request)
    }

    fun enviarEvento(tagAnalytics: TagAnalytics) {
        try {
            val request = FirebaseAnalyticsUseCase.RequestEvento(tagAnalytics)
            fireBaseAnalyticsViewState.postValue(FirebaseanalyticsViewState.EnviarEventoSuccessState(analytics.enviarEvento(request)))
        } catch (e: Exception){
            fireBaseAnalyticsViewState.postValue(FirebaseanalyticsViewState.ErrorState(e))
        }
    }

    fun enviarEventoMarcador(tagAnalytics: TagAnalytics, personaId: Long){
        val request = FirebaseAnalyticsUseCase.RequestEventoPorId(tagAnalytics, personaId)
        analytics.enviarEventoPersonaId(request)
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