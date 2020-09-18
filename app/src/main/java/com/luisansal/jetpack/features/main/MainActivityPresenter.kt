package com.luisansal.jetpack.features.main

import androidx.fragment.app.Fragment
import com.luisansal.jetpack.common.interfaces.ActionsViewPagerListener
import java.util.ArrayList

class MainActivityPresenter(private val view: MainActivityMVP.View, private val listener: ActionsViewPagerListener, private val position: Int?) : MainActivityMVP.Presenter {

    var interactor: MainActivityMVP.Interactor = MainActivityInteractor(this)

    override fun init() {
        boundFragments()
        view.setupTabPager()
        position?.let { listener.goTo(it) }
    }

    override fun setupViewPager(fragments: ArrayList<Fragment>) {
        view.setupViewPager(fragments)
    }

    override fun boundFragments() {
        interactor.boundFragments()
    }
}