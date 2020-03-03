package com.luisansal.jetpack.data.repository.di

import com.luisansal.jetpack.data.repository.FirebaseAnalyticsDataRepository
import com.luisansal.jetpack.data.repository.SesionDataRepository
import com.luisansal.jetpack.data.repository.UserRepository
import com.luisansal.jetpack.data.repository.UserVisitsRepository
import com.luisansal.jetpack.data.repository.datastore.FirebaseAnalyticsCloudDataStore
import com.luisansal.jetpack.data.repository.logs.EscribirArchivoLocalDataStore
import com.luisansal.jetpack.data.repository.logs.LogLocalDataRepository
import com.luisansal.jetpack.domain.logs.LogRepository
import com.luisansal.jetpack.domain.repository.FirebaseAnalyticsRepository
import org.koin.dsl.module

internal val repositoryModule = module {
    factory { UserRepository(get()) }
    factory { UserVisitsRepository(get()) }
    factory { SesionDataRepository(get(),get()) }
    factory<FirebaseAnalyticsRepository> { FirebaseAnalyticsDataRepository(get()) }
    factory { FirebaseAnalyticsCloudDataStore(get()) }
    factory<LogRepository> { LogLocalDataRepository(get()) }
    factory { EscribirArchivoLocalDataStore(get()) }
}