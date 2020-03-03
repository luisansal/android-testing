package com.luisansal.jetpack.domain.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbluser")
class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
    var dni: String = ""
    var name: String? = null
    var lastName: String? = null
}
