package com.luisansal.jetpack.features.onboarding

import android.content.Context
import android.os.Bundle
import android.view.View
import com.luisansal.jetpack.core.base.BaseBindingFragment
import com.luisansal.jetpack.databinding.FragmentOnboardingTwoBinding
import com.luisansal.jetpack.features.viewpager.TitleListener


class OnboardingTwoFragment : BaseBindingFragment(), TitleListener {
    private val binding by lazy { FragmentOnboardingTwoBinding.inflate(layoutInflater).apply { lifecycleOwner = this@OnboardingTwoFragment } }

    private var listener: PagerListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnOk.setOnClickListener {
            listener?.onStartLogin()
        }
    }

    override fun getViewResource() = binding.root

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when (context) {
            is PagerListener -> {
                listener = context
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override val title = "Onboarding Two"
}
