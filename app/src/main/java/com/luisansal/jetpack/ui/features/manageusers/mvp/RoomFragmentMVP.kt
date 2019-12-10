package com.luisansal.jetpack.ui.features.manageusers.mvp

interface RoomFragmentMVP{
    interface View {
        fun switchNavigation()
        fun getTagFragment() : String?
    }

    interface Presenter{
        fun init()
        fun switchNavigation()
    }
}
