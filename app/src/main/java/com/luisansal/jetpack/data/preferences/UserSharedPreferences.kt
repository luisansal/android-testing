package com.luisansal.jetpack.data.preferences

import android.content.SharedPreferences
import com.google.gson.Gson
import com.luisansal.jetpack.domain.entity.User
import com.luisansal.jetpack.utils.getString
import com.luisansal.jetpack.utils.putString

class UserSharedPreferences(private val preferences: SharedPreferences) {

    companion object {

        const val PREFERENCES_NAME = "UserPreferences"

        const val KEY_USER = "KEY_USER"
    }

    var user: User?
        get() = Gson().fromJson(preferences.getString(KEY_USER), User::class.java)
        set(value) = preferences.putString(KEY_USER, Gson().toJson(value))

    fun clear() {
        preferences.edit().clear().apply()
    }

}
