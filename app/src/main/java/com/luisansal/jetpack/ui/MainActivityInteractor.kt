package com.luisansal.jetpack.ui

import androidx.fragment.app.Fragment
import com.luisansal.jetpack.ui.features.maps.MapsFragment
import com.luisansal.jetpack.ui.features.manageusers.RoomFragment
import java.util.ArrayList

class MainActivityInteractor(var presenter: MainActivityMVP.Presenter) : MainActivityMVP.Interactor{

    override fun boundFragments() {
        val fragments = ArrayList<Fragment>()
        fragments.add(RoomFragment.newInstance())
        fragments.add(MapsFragment())
        presenter.setupActionBar(fragments)
        presenter.setupViewPager(fragments)
    }

}