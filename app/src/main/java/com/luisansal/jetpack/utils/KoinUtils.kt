package com.luisansal.jetpack.utils

import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

inline fun <reified T : Any> Fragment.injectFragment(): Lazy<T> =
        inject(parameters = { parametersOf(this) })