package com.luisansal.jetpack.domain.usecases.di

import com.luisansal.jetpack.domain.usecases.FirebaseAnalyticsUseCase
import com.luisansal.jetpack.domain.usecases.PopulateUseCase
import com.luisansal.jetpack.domain.usecases.UserUseCase
import com.luisansal.jetpack.domain.usecases.VisitUseCase
import org.koin.dsl.module

internal val usecasesModule = module {
    factory { FirebaseAnalyticsUseCase(get(), get(), get(), get(), get()) }
    factory { UserUseCase(get(),get()) }
    factory { VisitUseCase(get()) }
    factory { PopulateUseCase(get()) }
}