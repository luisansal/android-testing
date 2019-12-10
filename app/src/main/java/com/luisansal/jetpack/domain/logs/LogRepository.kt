package com.luisansal.jetpack.domain.logs

import com.luisansal.jetpack.domain.analytics.EventoModel
import com.luisansal.jetpack.domain.analytics.PantallaModel

interface LogRepository {
    fun guardarEvento(eventoModel: EventoModel): EventoModel
    fun guardarPantalla(pantallaModel: PantallaModel)
}