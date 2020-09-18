package com.luisansal.jetpack.features.main

import androidx.fragment.app.Fragment
import com.luisansal.jetpack.features.design.DesignFragment
import com.luisansal.jetpack.features.maps.MapsFragment
import com.luisansal.jetpack.features.manageusers.RoomFragment
import com.luisansal.jetpack.features.multimedia.MultimediaFragment
import java.util.ArrayList

class MainActivityInteractor(var presenter: MainActivityMVP.Presenter) : MainActivityMVP.Interactor {

    override fun boundFragments() {
        val fragments = ArrayList<Fragment>()
        fragments.add(RoomFragment.newInstance())
        fragments.add(MapsFragment.newInstance())
        fragments.add(MultimediaFragment.newInstance())
        fragments.add(DesignFragment.newInstance())
        presenter.setupViewPager(fragments)
    }

}