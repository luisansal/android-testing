package com.luisansal.jetpack.domain.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbluser")
class User(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Long = 0,
        var dni: String = "",
        var names: String = "",
        var lastNames: String = "",
        var fcbkId: String? = null,
        var address: String = "",
        var email: String = "",
        var emailConfirmation: Boolean = false,
        var emailVerifiedAt: Long = 0,
        var filePath: String? = null,
        var createdAt: Long = 0,
        var updatedAt: Long? = null
)
