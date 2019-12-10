package com.luisansal.jetpack.common.hardware.di

import com.luisansal.jetpack.common.hardware.AndroidHardwareInfoRetriever
import com.luisansal.jetpack.common.hardware.HardwareInfoRetriever
import org.koin.dsl.module

internal val hardwareModule = module {
    factory<HardwareInfoRetriever> { AndroidHardwareInfoRetriever(get()) }
}