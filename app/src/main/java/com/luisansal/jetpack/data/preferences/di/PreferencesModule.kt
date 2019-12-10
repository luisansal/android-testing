package com.luisansal.jetpack.data.preferences.di

import android.content.Context
import com.luisansal.jetpack.data.preferences.AuthSharedPreferences
import com.luisansal.jetpack.data.preferences.ConfigSharedPreferences
import com.luisansal.jetpack.data.preferences.SyncSharedPreferences
import com.luisansal.jetpack.data.preferences.UserSharedPreferences
import org.koin.dsl.module

val preferencesModule = module {
    single { UserSharedPreferences(get<Context>().userPreferences) }
    single { AuthSharedPreferences(get<Context>().authPreferences) }
    single { SyncSharedPreferences(get<Context>().syncPreferences) }
    single { ConfigSharedPreferences(get<Context>().configPreferences) }
}

private val Context.userPreferences
    get() = getSharedPreferences(UserSharedPreferences.PREFERENCES_NAME, Context.MODE_PRIVATE)

private val Context.authPreferences
    get() = getSharedPreferences(AuthSharedPreferences.PREFERENCES_NAME, Context.MODE_PRIVATE)

private val Context.syncPreferences
    get() = getSharedPreferences(SyncSharedPreferences.PREFERENCES_NAME, Context.MODE_PRIVATE)

private val Context.configPreferences
    get() = getSharedPreferences(ConfigSharedPreferences.PREFERENCES_NAME, Context.MODE_PRIVATE)
