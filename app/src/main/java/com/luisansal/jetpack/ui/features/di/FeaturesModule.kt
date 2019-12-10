package com.luisansal.jetpack.ui.features.di

import com.luisansal.jetpack.ui.features.analytics.FirebaseAnalyticsPresenter
import org.koin.dsl.module

internal val featuresModule = module {
    factory { FirebaseAnalyticsPresenter(get())}
}