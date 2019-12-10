package com.luisansal.jetpack.ui

import androidx.fragment.app.Fragment
import java.util.ArrayList

class MainActivityPresenter(var view: MainActivityMVP.View): MainActivityMVP.Presenter{

    var interactor : MainActivityMVP.Interactor = MainActivityInteractor(this)

    override fun init() {
        view.setupTabListener()
        boundFragments()
    }

    override fun setupViewPager(fragments: ArrayList<Fragment> ) {
        view.setupViewPager(fragments)
    }

    override fun setupActionBar(fragments: ArrayList<Fragment> ) {
        view.setupActionBar(fragments)
    }

    override fun boundFragments() {
        interactor.boundFragments()
    }


}