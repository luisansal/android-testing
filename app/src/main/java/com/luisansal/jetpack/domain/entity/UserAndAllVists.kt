package com.luisansal.jetpack.domain.entity

import androidx.room.Embedded
import androidx.room.Relation

class UserAndAllVists {

    @Embedded
    lateinit var user: User

    @Relation(parentColumn = "id", entityColumn = "userId", entity = Visit::class)
    lateinit var visits: List<Visit>
}
