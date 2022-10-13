package com.luisansal.jetpack.utils

interface ActionsViewPagerListener {
    var fragmentName :String?

    fun onNext()
    fun goTo(index: Int)
}