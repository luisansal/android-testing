package com.luisansal.jetpack.features.di

import com.luisansal.jetpack.features.analytics.FirebaseanalyticsViewModel
import com.luisansal.jetpack.features.auth.login.LoginViewModel
import com.luisansal.jetpack.features.auth.login.LoginViewModelFirebase
import com.luisansal.jetpack.features.auth.newuser.NewUserViewModel
import com.luisansal.jetpack.features.chat.ChatViewModel
import com.luisansal.jetpack.features.manageusers.listuser.ListUserViewModel
import com.luisansal.jetpack.features.manageusers.viewmodel.UserViewModel
import com.luisansal.jetpack.features.maps.MapsViewModel
import com.luisansal.jetpack.features.maps.viewmodels.MapsSearchViewModel
import com.luisansal.jetpack.features.multimedia.MultimediaViewModel
import com.luisansal.jetpack.features.populate.PopulateViewModel
import com.luisansal.jetpack.features.sales.products.ProductViewModel
import com.luisansal.jetpack.features.viewbinding.ViewBindingViewModel
import com.luisansal.jetpack.features.viewpager.ViewPagerPresenter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val featuresModule = module {
    viewModel { FirebaseanalyticsViewModel(get()) }
    viewModel { params -> com.luisansal.jetpack.features.manageusers.newuser.NewUserViewModel(params[0]) }
    factory { params -> ViewPagerPresenter(params[0], params[0]) }
    viewModel { UserViewModel(get(), get()) }
    viewModel { MapsViewModel(get(), get(), get()) }
    viewModel { PopulateViewModel(get()) }
    viewModel { MultimediaViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ChatViewModel(get()) }
    viewModel { MapsSearchViewModel(get()) }
    viewModel { ViewBindingViewModel(get(), get()) }
    viewModel { ProductViewModel() }
    viewModel { params -> LoginViewModelFirebase(params[0], get()) }
    viewModel { params -> NewUserViewModel(params[0], get()) }
    viewModel { ListUserViewModel() }
}