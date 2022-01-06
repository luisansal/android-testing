package com.luisansal.jetpack.data.repository

import com.luisansal.jetpack.core.data.preferences.AuthSharedPreferences
import com.luisansal.jetpack.data.preferences.UserSharedPreferences
import com.luisansal.jetpack.domain.entity.Rol
import com.luisansal.jetpack.domain.entity.Sesion
import com.luisansal.jetpack.core.domain.entity.User
import com.luisansal.jetpack.domain.repository.sesion.SesionRepository

class SesionDataRepository(
        private val userPreferences: UserSharedPreferences,
        private val authPreferences: AuthSharedPreferences
) : SesionRepository {

    override fun obtener(): Sesion? {

        if (!esSesionActiva()) return null

        val rol = recuperarRol()
        val persona = recuperarUsuario(rol)

        return Sesion(
                token = "",
                user = persona,
                codigoRol = requireNotNull(""),
                username = requireNotNull(""),
                codigoUsuario = requireNotNull("")
        )
    }

    override fun esSesionActiva(): Boolean {
        return authPreferences.isLogged
    }

    override fun setSessionState(logged: Boolean) {
        authPreferences.isLogged = logged
    }

    private fun recuperarRol(): Rol {
        val codigoRol = ""
        return Rol.Builder.construir(codigoRol)
    }

    private fun recuperarUsuario(rol: Rol) = when (rol) {
        Rol.WRITER -> obtenerWriter()
        Rol.READER -> obtenerReader()
        Rol.ADMIN -> obtenerAdmin()
        else -> obtenerPersonaGenerica()
    }

    private fun obtenerAdmin(): User {
        return User()
    }

    private fun obtenerReader(): User {
        return User()
    }

    private fun obtenerWriter(): User {
        return User()
    }

    private fun obtenerPersonaGenerica(): User {
        return User(
//                id = recuperarIdPorRol(Rol.NINGUNO),
//                primerNombre = userPreferences.nombre?.primeraPalabra(),
//                segundoNombre = null,
//                primerApellido = userPreferences.apellido?.primeraPalabra(),
//                segundoApellido = null,
//                email = userPreferences.correo,
//                ubicacion = recuperarUbicacion(),
//                tipoDocumento = Persona.TipoDocumento.NINGUNO,
//                documento = userPreferences.documento.orEmpty(),
//                cumpleanios = null,
//                fechaNacimiento = null,
//                directorio = DirectorioTelefonico.construirDummy()
        )
    }

    private fun saveProfileData() {


    }

}