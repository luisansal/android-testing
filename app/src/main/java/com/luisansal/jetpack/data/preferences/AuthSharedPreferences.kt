package com.luisansal.jetpack.data.preferences

import android.content.SharedPreferences
import com.luisansal.jetpack.utils.*

class AuthSharedPreferences(private val preferences: SharedPreferences) {

    companion object {

        const val PREFERENCES_NAME = "AuthPreferences"

        const val KEY_TOKEN = "token"
        const val KEY_TOKEN_TYPE = "KEY_TOKEN_TYPE"
        const val KEY_TOKEN_EXPIRES = "tokenExpires"

        const val KEY_LEGACY_TOKEN = "legacyToken"
        const val KEY_LEGACY_REFRESH_TOKEN = "legacyrefreshToken"

        const val KEY_FCM_TOKEN = "fcmToken"
        const val KEY_NEW_FCM_TOKEN = "newFcmToken"

        const val KEY_IS_LOGGED = "is_logged"

        const val KEY_USERNAME = "username"
        const val KEY_PASSWORD = "password"

    }

    var logged: Boolean
        get() = preferences.getBoolean(KEY_IS_LOGGED)
        set(value) = preferences.putBoolean(KEY_IS_LOGGED, value)

    var token: String?
        get() = preferences.getString(KEY_TOKEN)
        set(value) = preferences.putString(KEY_TOKEN, value)

    var tokenType: String?
        get() = preferences.getString(KEY_TOKEN_TYPE)
        set(value) = preferences.putString(KEY_TOKEN_TYPE, value)

    var tokenExpires: Long
        get() = preferences.getLong(KEY_TOKEN_EXPIRES)
        set(value) = preferences.putLong(KEY_TOKEN_EXPIRES, value)

    var legacyToken: String?
        get() = preferences.getString(KEY_LEGACY_TOKEN)
        set(value) = preferences.putString(KEY_LEGACY_TOKEN, value)

    var legacyRefreshToken: String?
        get() = preferences.getString(KEY_LEGACY_REFRESH_TOKEN)
        set(value) = preferences.putString(KEY_LEGACY_REFRESH_TOKEN, value)

    var username: String?
        get() = preferences.getString(KEY_USERNAME)
        set(value) = preferences.putString(KEY_USERNAME, value)

    var password: String?
        get() = preferences.getString(KEY_PASSWORD)
        set(value) = preferences.putString(KEY_PASSWORD, value)

    var fcmToken: String?
        get() = preferences.getString(KEY_FCM_TOKEN)
        set(value) = preferences.putString(KEY_FCM_TOKEN, value)

    var isNewFcmToken: Boolean
        get() = preferences.getBoolean(KEY_NEW_FCM_TOKEN)
        set(value) = preferences.putBoolean(KEY_NEW_FCM_TOKEN, value)

    fun saveLegacyTokens(token: String?, refreshToken: String?) {
        this.legacyToken = token
        this.legacyRefreshToken = refreshToken
    }

    fun clear() {
        preferences.edit().clear().apply()
    }

}
