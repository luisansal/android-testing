package com.luisansal.jetpack.data.di

import android.content.Context
import com.luisansal.jetpack.utils.listByElementsOf
import com.luisansal.jetpack.data.database.BaseRoomDatabase
import com.luisansal.jetpack.data.datastore.*
import com.luisansal.jetpack.domain.network.ApiService
import com.luisansal.jetpack.data.network.RetrofitConfig
import com.luisansal.jetpack.data.preferences.AuthSharedPreferences
import com.luisansal.jetpack.data.preferences.ConfigSharedPreferences
import com.luisansal.jetpack.data.preferences.SyncSharedPreferences
import com.luisansal.jetpack.data.preferences.UserSharedPreferences
import com.luisansal.jetpack.data.repository.*
import com.luisansal.jetpack.domain.logs.LogRepository
import com.luisansal.jetpack.domain.network.MapsApiService
import com.luisansal.jetpack.domain.repository.FirebaseAnalyticsRepository
import org.koin.core.module.Module
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

val databaseModule = module {
    single { BaseRoomDatabase.getDatabase(get()) }
}

val networkModule = module {
    single { RetrofitConfig(ApiService.BASE_URL, get()).creteService(ApiService::class.java) }
    single { RetrofitConfig(MapsApiService.GMAPS_PLACES_URL, get()).creteService(MapsApiService::class.java) }
}

internal val dataStoreModule = module {
    factory { FirebaseAnalyticsCloudDataStore(get()) }
    factory { EscribirArchivoLocalDataStore(get()) }
    factory { AuthCloudStore(get(), get(), get()) }
    factory { ChatCloudStore(get()) }
    factory { MapsCloudStore(get(), get(), get()) }
}

internal val repositoryModule = module {
    factory { UserRepository(get()) }
    factory { VisitRepository(get()) }
    factory { SesionDataRepository(get(), get()) }
    factory<FirebaseAnalyticsRepository> { FirebaseAnalyticsDataRepository(get()) }
    factory<LogRepository> { LogLocalDataRepository(get()) }
    factory { PopulateRepository(get()) }
    factory { MapsRepository(get()) }
}

internal val dataModule by lazy {
    listByElementsOf<Module>(preferencesModule, repositoryModule, dataStoreModule, databaseModule, networkModule)
}