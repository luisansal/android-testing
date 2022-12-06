package com.luisansal.jetpack.data.preferences

import android.content.SharedPreferences
import com.google.gson.Gson
import com.luisansal.jetpack.core.domain.entities.User
import com.luisansal.jetpack.core.utils.getString
import com.luisansal.jetpack.core.utils.putLong
import com.luisansal.jetpack.core.utils.getLong
import com.luisansal.jetpack.core.utils.putString

class UserSharedPreferences(private val preferences: SharedPreferences) {

    companion object {
        const val PREFERENCES_NAME = "UserPreferences"
        const val KEY_USER = "KEY_USER"
        const val KEY_COUNTDOWN_START_TIME = "KEY_COUNTDOWN_START_TIME"
        const val KEY_COUNTDOWN_END_TIME = "KEY_COUNTDOWN_END_TIME"
        const val KEY_TIME_TO_COUNTDOWN = "KEY_TIME_TO_COUNTDOWN"
    }

    var user: User?
        get() = Gson().fromJson(preferences.getString(KEY_USER), User::class.java)
        set(value) = preferences.putString(KEY_USER, Gson().toJson(value))

    var countDownStartTime: Long
        get() = preferences.getLong(KEY_COUNTDOWN_START_TIME)
        set(value) = preferences.putLong(KEY_COUNTDOWN_START_TIME, value)

    var countDownEndTime: Long
        get() = preferences.getLong(KEY_COUNTDOWN_END_TIME)
        set(value) = preferences.putLong(KEY_COUNTDOWN_END_TIME, value)

    var timeToCountDown: Long
        get() = preferences.getLong(KEY_TIME_TO_COUNTDOWN)
        set(value) = preferences.putLong(KEY_TIME_TO_COUNTDOWN, value)

    fun clear() {
        preferences.edit().clear().apply()
    }

}
