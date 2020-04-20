package com.luisansal.jetpack.ui.features.di

import com.luisansal.jetpack.ui.PopulateViewModel
import com.luisansal.jetpack.ui.features.analytics.FirebaseanalyticsViewModel
import com.luisansal.jetpack.ui.features.manageusers.newuser.NewUserPresenter
import com.luisansal.jetpack.ui.features.manageusers.viewmodel.UserViewModel
import com.luisansal.jetpack.ui.features.maps.MapsViewModel
import com.luisansal.jetpack.ui.features.multimedia.MultimediaViewModel
import org.koin.dsl.module

internal val featuresModule = module {
    factory { FirebaseanalyticsViewModel(get()) }
    factory { params ->
        NewUserPresenter(params[0], get())
    }
    factory { UserViewModel(get()) }
    factory { MapsViewModel(get(),get()) }
    factory { PopulateViewModel(get()) }
    factory { MultimediaViewModel(get()) }
}