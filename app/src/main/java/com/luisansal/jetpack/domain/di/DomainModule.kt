package com.luisansal.jetpack.domain.di

import com.luisansal.jetpack.domain.usecases.di.usecasesModule

internal val domainModule by lazy {
    listOf(usecasesModule)
}