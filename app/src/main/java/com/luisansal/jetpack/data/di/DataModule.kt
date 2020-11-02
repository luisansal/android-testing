package com.luisansal.jetpack.data.di

import com.luisansal.jetpack.common.utils.listByElementsOf
import com.luisansal.jetpack.data.database.BaseRoomDatabase
import com.luisansal.jetpack.data.datastore.*
import com.luisansal.jetpack.domain.network.ApiService
import com.luisansal.jetpack.data.network.RetrofitConfig
import com.luisansal.jetpack.data.preferences.di.preferencesModule
import com.luisansal.jetpack.data.repository.*
import com.luisansal.jetpack.domain.logs.LogRepository
import com.luisansal.jetpack.domain.repository.FirebaseAnalyticsRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val databaseModule = module {
    factory { BaseRoomDatabase.getDatabase(get()) }
}

val networkModule = module {
    factory { RetrofitConfig(ApiService.BASE_URL, get()).creteService(ApiService::class.java) }
}

internal val dataStoreModule = module {
    factory { FirebaseAnalyticsCloudDataStore(get()) }
    factory { EscribirArchivoLocalDataStore(get()) }
    factory { AuthCloudStore(get(), get(), get()) }
    factory { ChatCloudStore(get()) }
    factory { MapsCloudStore(get()) }
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