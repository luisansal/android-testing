package com.luisansal.jetpack.features.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager, private val type: OnboardingEnum) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments: List<Fragment>
        get() {
            return when (type) {
                OnboardingEnum.Basic -> {
                    listOf(OnboardingOneFragment(), OnboardingTwoFragment())
                }
                OnboardingEnum.Advanced -> {
                    listOf(OnboardingOneFragment(), OnboardingTwoFragment(), OnboardingThreeFragment())
                }
            }
        }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

}