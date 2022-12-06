package com.luisansal.jetpack.core.domain.daos

import com.google.gson.GsonBuilder
import com.luisansal.jetpack.core.domain.entities.BaseEntity
import kotlin.reflect.KClass

abstract class BaseDao : BaseEntity() {
    fun <ConvertTo : BaseEntity> toEntity(_class: KClass<ConvertTo>): ConvertTo {
        val gson = GsonBuilder().create()
        val jsonDao = gson.toJson(this)
        return gson.fromJson(jsonDao, _class.java) as ConvertTo
    }
}

fun BaseDao.toSearch(text: String): List<String> {
    var substringArray = mutableListOf<String>()
    val textLowercased = text.lowercase()

    for (item in textLowercased.split("$$")) {
        val characterCount = item.length
        for (num in 1..characterCount) {
            substringArray.add(item.substring(0, num))
        }
    }

    return substringArray
}