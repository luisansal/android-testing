package com.luisansal.jetpack.common.interfaces

interface ActionsViewPagerListener {
    var fragmentName :String?

    fun onNext()
    fun goTo(index: Int)
}
