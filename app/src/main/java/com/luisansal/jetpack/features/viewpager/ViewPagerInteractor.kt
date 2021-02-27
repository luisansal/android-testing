package com.luisansal.jetpack.features.viewpager

import androidx.fragment.app.Fragment
import com.luisansal.jetpack.features.onboarding.OnboardingOneFragment
import com.luisansal.jetpack.features.onboarding.OnboardingTwoFragment
import java.util.ArrayList

class ViewPagerInteractor(var presenter: ViewPagerMVP.Presenter) : ViewPagerMVP.Interactor {

    override fun boundFragments() {
        val fragments = ArrayList<Fragment>()
        fragments.add(OnboardingOneFragment())
        fragments.add(OnboardingTwoFragment())
        presenter.setupViewPager(fragments)
    }

}