package com.luisansal.jetpack.core.domain.daos

import com.luisansal.jetpack.core.utils.EMPTY

data class CityDao(
    val CiudadId: Long = 0,
    var CiudadNombre: String = EMPTY,
    var PaisCodigo: String = EMPTY,
    var CiudadDistrito: String = EMPTY,
    var CiudadPoblacion: Int = 0,
) : BaseDao(){
    override fun toString(): String {
        return "$CiudadNombre"
    }

}