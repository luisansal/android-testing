package com.luisansal.jetpack.domain.usecases.di

import com.luisansal.jetpack.domain.usecases.*
import org.koin.dsl.module

internal val usecasesModule = module {
    factory { FirebaseAnalyticsUseCase(get(), get(), get(), get(), get()) }
    factory { UserUseCase(get(), get()) }
    factory { VisitUseCase(get()) }
    factory { PopulateUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { ChatUseCase(get()) }
    factory { MapsUseCase(get()) }
}