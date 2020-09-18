package com.luisansal.jetpack.data.preferences

import android.content.SharedPreferences
import com.luisansal.jetpack.utils.getLong
import com.luisansal.jetpack.utils.putLong

class SyncSharedPreferences(private val preferences: SharedPreferences) {

    companion object {

        const val PREFERENCES_NAME = "SyncPreferences"

        const val KEY_BASE_SYNC_DATE = "baseSyncDate"
        const val KEY_CONSULTANTS_SYNC_DATE = "consultantsSyncDate"
        const val KEY_POSTULANTS_SYNC_DATE = "postulantsSyncDate"

    }

    var baseSyncDate: Long?
        get() = preferences.getLong(KEY_BASE_SYNC_DATE)
        set(value) = preferences.putLong(KEY_BASE_SYNC_DATE, value ?: 0)

    var consultantsSyncDate: Long?
        get() = preferences.getLong(KEY_CONSULTANTS_SYNC_DATE)
        set(value) = preferences.putLong(KEY_CONSULTANTS_SYNC_DATE, value ?: 0)

    var postulantsSyncDate: Long?
        get() = preferences.getLong(KEY_POSTULANTS_SYNC_DATE)
        set(value) = preferences.putLong(KEY_POSTULANTS_SYNC_DATE, value ?: 0)

    fun clear() {
        preferences.edit().clear().apply()
    }

}
