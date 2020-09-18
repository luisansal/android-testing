package com.luisansal.jetpack.di

import android.app.Application
import com.luisansal.jetpack.common.utils.listByElementsOf
import com.luisansal.jetpack.features.baseModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

internal fun injectIntegrationTestModules(app: Application) {
    startKoin {
        androidLogger()
        androidContext(app)
        modules(baseIntegrationTestModules)
    }
}

internal val baseIntegrationTestModules by lazy {
    listByElementsOf<Module>(
            baseModules,
        baseIntegrationMocksModule
    )
}

internal val baseIntegrationMocksModule = module {

}
