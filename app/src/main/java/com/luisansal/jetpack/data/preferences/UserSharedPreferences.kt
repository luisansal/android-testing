package com.luisansal.jetpack.data.preferences

import android.content.SharedPreferences
import com.luisansal.jetpack.ui.utils.getString
import com.luisansal.jetpack.ui.utils.putString

class UserSharedPreferences(private val preferences: SharedPreferences) {

    companion object {

        const val PREFERENCES_NAME = "UserPreferences"

        const val KEY_ROL = "rol"
        const val KEY_COD_ROL = "cod_rol"
        const val KEY_COD_USUARIO = "codUsuario"

        const val KEY_REGION = "region"
        const val KEY_ZONA = "zona"
        const val KEY_SECCION = "seccion"
        const val KEY_PAIS = "pais"
        const val KEY_COD_PAIS = "cod_pais"

        const val KEY_FIRST_NAME = "first_name"
        const val KEY_LAST_NAME = "last_name"
        const val KEY_SECOND_NAME = "second_name"
        const val KEY_DOCUMENTO = "documento_identidad"
        const val KEY_COD_CONSULTORA = "codConsultora"
        const val KEY_USERNAME = "username"

        const val KEY_CUB = "cub"
        const val KEY_NIVEL = "nivel"
        const val KEY_TELEFONO_MOVIL = "telefono_movil"
        const val KEY_TELEFONO_FIJO = "telefono_fijo"
        const val KEY_EMAIL = "email"
        const val KEY_CONSULTORA_ID = "consultora_id"
        const val KEY_LATITUD = "latitud"
        const val KEY_LONGITUD = "longitud"
        const val KEY_COD_TERRITORIO = "codTerritorio"
        const val KEY_SECCION_GESTION_LIDER = "seccionGestionLider"
        const val KEY_GEOREFERENCIA = "georeferencia"

    }

    var codUsuario: String?
        get() = preferences.getString(KEY_COD_USUARIO)
        set(value) = preferences.putString(KEY_COD_USUARIO, value)

    var rol: String?
        get() = preferences.getString(KEY_ROL)
        set(value) = preferences.putString(KEY_ROL, value)

    var codRol: String?
        get() = preferences.getString(KEY_COD_ROL)
        set(value) = preferences.putString(KEY_COD_ROL, value)

    var region: String?
        get() = preferences.getString(KEY_REGION)
        set(value) = preferences.putString(KEY_REGION, value)

    var zona: String?
        get() = preferences.getString(KEY_ZONA)
        set(value) = preferences.putString(KEY_ZONA, value)

    var seccion: String?
        get() = preferences.getString(KEY_SECCION)
        set(value) = preferences.putString(KEY_SECCION, value)

    var pais: String?
        get() = preferences.getString(KEY_PAIS)
        set(value) = preferences.putString(KEY_PAIS, value)

    var codPais: String?
        get() = preferences.getString(KEY_COD_PAIS)
        set(value) = preferences.putString(KEY_COD_PAIS, value)

    var nombre: String?
        get() = preferences.getString(KEY_FIRST_NAME)
        set(value) = preferences.putString(KEY_FIRST_NAME, value)

    var apellido: String?
        get() = preferences.getString(KEY_LAST_NAME)
        set(value) = preferences.putString(KEY_LAST_NAME, value)

    var apellidoMaterno: String?
        get() = preferences.getString(KEY_SECOND_NAME)
        set(value) = preferences.putString(KEY_SECOND_NAME, value)

    var documento: String?
        get() = preferences.getString(KEY_DOCUMENTO)
        set(value) = preferences.putString(KEY_DOCUMENTO, value)

    var codConsultora: String?
        get() = preferences.getString(KEY_COD_CONSULTORA)
        set(value) = preferences.putString(KEY_COD_CONSULTORA, value)

    var username: String?
        get() = preferences.getString(KEY_USERNAME)
        set(value) = preferences.putString(KEY_USERNAME, value)

    var cub: String?
        get() = preferences.getString(KEY_CUB)
        set(value) = preferences.putString(KEY_CUB, value)

    var nivel: String?
        get() = preferences.getString(KEY_NIVEL)
        set(value) = preferences.putString(KEY_NIVEL, value)

    var celular: String?
        get() = preferences.getString(KEY_TELEFONO_MOVIL)
        set(value) = preferences.putString(KEY_TELEFONO_MOVIL, value)

    var telefono: String?
        get() = preferences.getString(KEY_TELEFONO_FIJO)
        set(value) = preferences.putString(KEY_TELEFONO_FIJO, value)

    var correo: String?
        get() = preferences.getString(KEY_EMAIL)
        set(value) = preferences.putString(KEY_EMAIL, value)

    var consultoraId: String?
        get() = preferences.getString(KEY_CONSULTORA_ID)
        set(value) = preferences.putString(KEY_CONSULTORA_ID, value)

    var latitud: String?
        get() = preferences.getString(KEY_LATITUD)
        set(value) = preferences.putString(KEY_LATITUD, value)

    var longitud: String?
        get() = preferences.getString(KEY_LONGITUD)
        set(value) = preferences.putString(KEY_LONGITUD, value)

    var codTerritorio: String?
        get() = preferences.getString(KEY_COD_TERRITORIO)
        set(value) = preferences.putString(KEY_COD_TERRITORIO, value)

    var seccionGestionLider: String?
        get() = preferences.getString(KEY_SECCION_GESTION_LIDER)
        set(value) = preferences.putString(KEY_SECCION_GESTION_LIDER, value)

    var georeferencia: String?
        get() = preferences.getString(KEY_GEOREFERENCIA)
        set(value) = preferences.putString(KEY_GEOREFERENCIA, value)

    fun clear() {
        preferences.edit().clear().apply()
    }

}
