package com.luisansal.jetpack.data.di

import com.luisansal.jetpack.common.utils.listByElementsOf
import com.luisansal.jetpack.data.database.BaseRoomDatabase
import com.luisansal.jetpack.data.preferences.di.preferencesModule
import com.luisansal.jetpack.data.repository.di.repositoryModule
import org.koin.core.module.Module
import org.koin.dsl.module

val databaseModule = module {
    factory { BaseRoomDatabase.getDatabase(get()) }
}

internal val dataModule by lazy {
    listByElementsOf<Module>(preferencesModule, repositoryModule, databaseModule)
}