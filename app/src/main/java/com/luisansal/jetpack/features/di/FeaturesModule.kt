package com.luisansal.jetpack.features.di

import com.luisansal.jetpack.features.analytics.FirebaseanalyticsViewModel
import com.luisansal.jetpack.features.auth.login.LoginViewModel
import com.luisansal.jetpack.features.auth.login.LoginViewModelFirebase
import com.luisansal.jetpack.features.auth.newuser.NewuserViewModel
import com.luisansal.jetpack.features.chat.ChatViewModel
import com.luisansal.jetpack.features.manageusers.newuser.NewUserPresenter
import com.luisansal.jetpack.features.manageusers.viewmodel.UserViewModel
import com.luisansal.jetpack.features.maps.MapsViewModel
import com.luisansal.jetpack.features.maps.viewmodels.MapsSearchViewModel
import com.luisansal.jetpack.features.multimedia.MultimediaViewModel
import com.luisansal.jetpack.features.populate.PopulateViewModel
import com.luisansal.jetpack.features.sales.products.ProductViewModel
import com.luisansal.jetpack.features.viewbinding.ViewBindingViewModel
import com.luisansal.jetpack.features.viewpager.ViewPagerPresenter
import org.koin.dsl.module

internal val featuresModule = module {
    factory { FirebaseanalyticsViewModel(get()) }
    factory { params ->
        NewUserPresenter(params[0], get(), get())
    }
    factory { params ->
        ViewPagerPresenter(params[0], params[0])
    }
    factory { UserViewModel(get()) }
    factory { MapsViewModel(get(), get(), get()) }
    factory { PopulateViewModel(get()) }
    factory { MultimediaViewModel(get()) }
    factory { LoginViewModel(get()) }
    factory { ChatViewModel(get()) }
    factory { MapsSearchViewModel(get()) }
    factory { ViewBindingViewModel(get(), get()) }
    factory { ProductViewModel() }
    factory { params -> LoginViewModelFirebase(params[0], get()) }
    factory { params -> NewuserViewModel(params[0], get()) }
}