package com.luisansal.jetpack.core.domain.entities

import com.google.gson.GsonBuilder
import com.luisansal.jetpack.core.domain.daos.BaseDao
import java.io.Serializable
import java.util.*
import kotlin.reflect.KClass

abstract class BaseEntity : Serializable{
    var createAt: Long = Calendar.getInstance().timeInMillis
    var updatedAt: Long = 0
    var status: Boolean = true

    fun <ConvertTo : BaseDao> toDao(_class: KClass<ConvertTo>): ConvertTo {
        val gson = GsonBuilder().create()
        val jsonEntity = gson.toJson(this)
        return gson.fromJson(jsonEntity, _class.java) as ConvertTo
    }
}