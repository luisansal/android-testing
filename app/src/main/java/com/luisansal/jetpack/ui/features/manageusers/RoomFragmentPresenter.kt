package com.luisansal.jetpack.ui.features.manageusers

class RoomFragmentPresenter(var mView: RoomFragmentMVP.View) : RoomFragmentMVP.Presenter{

    override fun init() {
        switchNavigation()
    }

    override fun switchNavigation() {
        mView.switchNavigation()
    }

}