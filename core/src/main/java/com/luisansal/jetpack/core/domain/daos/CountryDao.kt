package com.luisansal.jetpack.core.domain.daos

import com.luisansal.jetpack.core.utils.EMPTY

data class CountryDao(
    val PaisCodigo: String = EMPTY,
    var PaisNombre: String = EMPTY,
    var PaisContinente: String = EMPTY,
    var PaisRegion: String = EMPTY,
    var PaisArea: Double = 0.0,
    var PaisIndependencia : Int = 0,
    var PaisPoblacion : Int = 0,
    var PaisExpectativaDeVida : Double = 0.0,
    var PaisProductoInternoBruto : Double = 0.0,
    var PaisProductoInternoBrutoAntiguo : Double = 0.0,
    var PaisNombreLocal : String = EMPTY,
    var PaisGobierno : String = EMPTY,
    var PaisJefeDeEstado : String = EMPTY,
    var PaisCapital : Int = 0,
    var PaisCodigo2 : String = EMPTY,
) : BaseDao(){
    override fun toString(): String {
        return "$PaisNombre"
    }

}