package com.luisansal.jetpack.domain.repository.sesion

import com.luisansal.jetpack.domain.entity.Sesion

interface SesionRepository {
    fun obtener(): Sesion?
    fun setSessionState(logged: Boolean)
    fun esSesionActiva(): Boolean
}