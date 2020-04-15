package com.luisansal.jetpack.data.repository.di

import com.luisansal.jetpack.data.repository.*
import com.luisansal.jetpack.data.repository.datastore.FirebaseAnalyticsCloudDataStore
import com.luisansal.jetpack.data.repository.logs.EscribirArchivoLocalDataStore
import com.luisansal.jetpack.data.repository.logs.LogLocalDataRepository
import com.luisansal.jetpack.domain.logs.LogRepository
import com.luisansal.jetpack.domain.repository.FirebaseAnalyticsRepository
import org.koin.dsl.module

internal val repositoryModule = module {
    factory { UserRepository(get()) }
    factory { VisitRepository(get()) }
    factory { SesionDataRepository(get(), get()) }
    factory<FirebaseAnalyticsRepository> { FirebaseAnalyticsDataRepository(get()) }
    factory { FirebaseAnalyticsCloudDataStore(get()) }
    factory<LogRepository> { LogLocalDataRepository(get()) }
    factory { EscribirArchivoLocalDataStore(get()) }
    factory { PopulateRepository(get()) }
}