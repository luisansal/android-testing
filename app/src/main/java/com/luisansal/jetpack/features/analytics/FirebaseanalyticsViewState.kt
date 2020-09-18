package com.luisansal.jetpack.features.analytics

import com.luisansal.jetpack.domain.analytics.EventoModel
import java.lang.Exception

sealed class FirebaseanalyticsViewState {
    data class ErrorState(val e: Exception) : FirebaseanalyticsViewState()
    data class EnviarEventoSuccessState(val eventoModel : EventoModel) : FirebaseanalyticsViewState()
    data class MostrarUsuariosSuccessState(val eventoModel : EventoModel) : FirebaseanalyticsViewState()
}