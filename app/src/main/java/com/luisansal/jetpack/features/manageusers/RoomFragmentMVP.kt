package com.luisansal.jetpack.features.manageusers

interface RoomFragmentMVP{
    interface View {
        fun switchNavigation()
        var tagFragment : String?
    }

    interface Presenter{
        fun init()
        fun switchNavigation()
    }
}
