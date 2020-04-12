package com.luisansal.jetpack.ui.features.di

import com.luisansal.jetpack.ui.features.analytics.FirebaseAnalyticsPresenter
import com.luisansal.jetpack.ui.features.manageusers.newuser.NewUserPresenter
import com.luisansal.jetpack.ui.features.manageusers.viewmodel.UserViewModel
import com.luisansal.jetpack.ui.features.maps.MapsViewModel
import org.koin.dsl.module

internal val featuresModule = module {
    factory { FirebaseAnalyticsPresenter(get()) }
    factory { params ->
        NewUserPresenter(params[0], get())
    }

    factory { UserViewModel(get()) }
    factory { MapsViewModel(get(),get()) }
}