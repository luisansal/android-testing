package com.luisansal.jetpack.features.viewpager

import java.util.ArrayList

import androidx.fragment.app.Fragment

interface ViewPagerMVP {
    interface View {
        fun setupTabPager()
        fun setupViewPager(fragments: ArrayList<Fragment>)
    }

    interface Presenter {
        fun init(position: Int?)
        fun setupViewPager(fragments: ArrayList<Fragment>)
        fun boundFragments()
    }

    interface Interactor {
        fun boundFragments()
    }
}
