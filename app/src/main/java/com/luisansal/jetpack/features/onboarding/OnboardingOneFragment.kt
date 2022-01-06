package com.luisansal.jetpack.features.onboarding

import android.os.Bundle
import android.view.View
import com.luisansal.jetpack.R
import com.luisansal.jetpack.core.base.BaseFragment
import com.luisansal.jetpack.features.viewpager.TitleListener


class OnboardingOneFragment : BaseFragment(), TitleListener {

    override fun getViewIdResource() = R.layout.fragment_onboarding_one

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override val title ="Onboarding One"

}
