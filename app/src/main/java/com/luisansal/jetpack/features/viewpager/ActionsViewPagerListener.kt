package com.luisansal.jetpack.features.viewpager

interface ActionsViewPagerListener {
    var fragmentName :String?

    fun onNext()
    fun goTo(index: Int)
}
