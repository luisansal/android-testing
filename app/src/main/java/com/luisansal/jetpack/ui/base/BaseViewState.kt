package com.luisansal.jetpack.ui.base

open class BaseViewState<T> {

    var data: T? = null

    var error: Throwable? = null

    enum class State(var value: Int) {
        LOADING(0), SUCCESS(1), FAILED(-1);
    }
}