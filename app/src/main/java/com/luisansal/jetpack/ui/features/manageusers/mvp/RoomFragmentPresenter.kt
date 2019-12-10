package com.luisansal.jetpack.ui.features.manageusers.mvp

import com.luisansal.jetpack.ui.features.manageusers.mvp.RoomFragmentMVP

class RoomFragmentPresenter(var mView: RoomFragmentMVP.View) : RoomFragmentMVP.Presenter{

    override fun init() {
        switchNavigation()
    }

    override fun switchNavigation() {
        mView.switchNavigation()
    }

}