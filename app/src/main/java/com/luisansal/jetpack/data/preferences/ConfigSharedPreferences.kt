package com.luisansal.jetpack.data.preferences

import android.content.SharedPreferences
import com.luisansal.jetpack.core.utils.putLong

class ConfigSharedPreferences(private val preferences: SharedPreferences) {

    companion object {

        const val PREFERENCES_NAME = "ConfigPreferences"

        const val KEY_CACHE_MAX_AGE = "cache_max_age"
        const val KEY_CACHE_MAX_STALE = "cache_max_stale"

        const val DEFAULT_CACHE_MAX_AGE = 5L
        const val DEFAULT_CACHE_MAX_STALE = 86400L

    }

    var maxAge: Long?
        get() = preferences.getLong(KEY_CACHE_MAX_AGE, DEFAULT_CACHE_MAX_AGE)
        set(value) = preferences.putLong(KEY_CACHE_MAX_AGE, value ?: 0)

    var maxStale: Long?
        get() = preferences.getLong(KEY_CACHE_MAX_STALE, DEFAULT_CACHE_MAX_STALE)
        set(value) = preferences.putLong(KEY_CACHE_MAX_STALE, value ?: 0)

}
