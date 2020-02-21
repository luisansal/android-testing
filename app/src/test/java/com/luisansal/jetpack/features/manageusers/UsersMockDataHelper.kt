package com.luisansal.jetpack.features.manageusers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.utils.readString

class UsersMockDataHelper {

    fun getUsers(): List<User> {
        val jsonString = readString("features/manageusers/users.json")
        val users = Gson().fromJson<List<User>>(jsonString, object : TypeToken<List<User?>?>() {}.type)
        return users
    }

}