package com.luisansal.jetpack.domain.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "tbluser_tblvisit_join",
        primaryKeys = arrayOf("userId","visitId"),
        foreignKeys = arrayOf(
                ForeignKey(entity = User::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("userId")),
                ForeignKey(entity = Visit::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("visitId"))
        )
)
data class UserVisitJoin(val userId: Long,
                         val visitId: Long)
