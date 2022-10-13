package com.luisansal.jetpack.utils.hardware.di

import com.luisansal.jetpack.utils.hardware.AndroidHardwareInfoRetriever
import com.luisansal.jetpack.utils.hardware.HardwareInfoRetriever
import org.koin.dsl.module

internal val hardwareModule = module {
    factory<HardwareInfoRetriever> { AndroidHardwareInfoRetriever(get()) }
}