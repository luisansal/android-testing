package com.luisansal.jetpack.ui

import android.app.Application
import com.google.android.gms.common.util.ArrayUtils
import com.luisansal.jetpack.common.hardware.di.hardwareModule
import com.luisansal.jetpack.common.utils.listByElementsOf
import com.luisansal.jetpack.data.di.dataModule
import com.luisansal.jetpack.domain.di.domainModule
import com.luisansal.jetpack.ui.features.di.featuresModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

val baseModules by lazy {
    listByElementsOf<Module>(
            dataModule, domainModule, featuresModule, hardwareModule
    )
}

open class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Android context
            androidContext(this@MyApplication)
            // modules
            modules(baseModules)
        }
    }
}