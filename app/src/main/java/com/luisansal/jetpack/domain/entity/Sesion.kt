package com.luisansal.jetpack.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.luisansal.jetpack.core.domain.entities.User

@Entity(tableName = "tblsesion")
class Sesion(@PrimaryKey(autoGenerate = true)
             @ColumnInfo(name = "token")
             val token: String,
             val codigoRol: String,
             val codigoUsuario: String,
             val username: String,
             val user: User
             ) {

    val rol = Rol.Builder.construir(codigoRol)
}