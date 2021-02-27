package com.luisansal.jetpack.features.viewpager

import androidx.fragment.app.Fragment
import java.util.ArrayList

class ViewPagerPresenter(private val view: ViewPagerMVP.View, private val listener: ActionsViewPagerListener) : ViewPagerMVP.Presenter {

    var interactor: ViewPagerMVP.Interactor = ViewPagerInteractor(this)

    override fun init(position: Int?) {
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