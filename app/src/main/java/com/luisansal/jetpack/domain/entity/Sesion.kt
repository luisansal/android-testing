package com.luisansal.jetpack.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.luisansal.jetpack.domain.model.user.Rol

@Entity(tableName = "tblsesion")
class Sesion(@PrimaryKey(autoGenerate = true)
             @ColumnInfo(name = "token")
             val token: String,
             val codigoRol: String,
             val codigoUsuario: String,
             val username: String,
             val user: User,
             val nivel: String?) {

    val rol = Rol.Builder.construir(codigoRol)
}