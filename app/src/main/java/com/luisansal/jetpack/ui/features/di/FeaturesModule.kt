package com.luisansal.jetpack.ui.features.di

import com.luisansal.jetpack.ui.features.analytics.FirebaseAnalyticsPresenter
import com.luisansal.jetpack.ui.features.manageusers.newuser.NewUserPresenter
import org.koin.dsl.module

internal val featuresModule = module {
    factory { FirebaseAnalyticsPresenter(get()) }
    factory { params ->
        NewUserPresenter(params[0], get())
    }
}