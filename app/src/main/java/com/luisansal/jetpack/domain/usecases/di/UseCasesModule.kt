package com.luisansal.jetpack.domain.usecases.di

import com.luisansal.jetpack.domain.usecases.FirebaseAnalyticsUseCase
import org.koin.dsl.module

internal val usecasesModule = module {
    factory { FirebaseAnalyticsUseCase(get(), get(), get(), get(), get()) }
}