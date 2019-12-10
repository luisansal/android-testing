package com.luisansal.jetpack.data.di

import com.luisansal.jetpack.common.utils.listByElementsOf
import com.luisansal.jetpack.data.preferences.di.preferencesModule
import com.luisansal.jetpack.data.repository.di.repositoryModule
import org.koin.core.module.Module

internal val dataModule by lazy {
    listByElementsOf<Module>(preferencesModule, repositoryModule)
}