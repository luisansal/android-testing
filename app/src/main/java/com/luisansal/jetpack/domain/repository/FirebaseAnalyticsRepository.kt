package com.luisansal.jetpack.domain.repository

import com.luisansal.jetpack.domain.analytics.EventoModel
import com.luisansal.jetpack.domain.analytics.PantallaModel

interface FirebaseAnalyticsRepository {
    fun enviarEvento(eventoModel: EventoModel): EventoModel
    fun enviarPantalla(pantallaModel: PantallaModel)
}