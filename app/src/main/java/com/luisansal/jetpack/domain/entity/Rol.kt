package com.luisansal.jetpack.domain.entity


enum class Rol(val codigoRol: String) {
    READER(Builder.COD_READER),
    WRITER(Builder.COD_WRITER),
    ADMIN(Builder.COD_ADMIN),
    NINGUNO("");

    class Builder {
        companion object {
            const val COD_READER = "READER"
            const val COD_WRITER = "WRITER"
            const val COD_ADMIN = "ADMIN"

            fun construir(codigoRol: String?): Rol {
                return when (codigoRol) {
                    COD_READER -> READER
                    COD_WRITER -> WRITER
                    COD_ADMIN -> ADMIN
                    else -> NINGUNO
                }
            }
        }
    }
}
