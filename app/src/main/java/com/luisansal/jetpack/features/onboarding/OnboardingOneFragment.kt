package com.luisansal.jetpack.features.onboarding

import android.os.Bundle
import android.view.View
import com.luisansal.jetpack.R
import com.luisansal.jetpack.core.base.BaseBindingFragment
import com.luisansal.jetpack.core.base.BaseFragment
import com.luisansal.jetpack.databinding.FragmentOnboardingOneBinding
import com.luisansal.jetpack.features.viewpager.TitleListener


class OnboardingOneFragment : BaseBindingFragment(), TitleListener {
    private val binding by lazy { FragmentOnboardingOneBinding.inflate(layoutInflater).apply { lifecycleOwner = this@OnboardingOneFragment } }
    override fun getViewResource() = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override val title ="Onboarding One"

}
