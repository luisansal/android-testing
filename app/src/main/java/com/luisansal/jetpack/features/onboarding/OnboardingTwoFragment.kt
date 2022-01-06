package com.luisansal.jetpack.features.onboarding

import android.content.Context
import android.os.Bundle
import android.view.View
import com.luisansal.jetpack.R
import com.luisansal.jetpack.core.base.BaseFragment
import com.luisansal.jetpack.features.viewpager.TitleListener
import kotlinx.android.synthetic.main.fragment_onboarding_two.*


class OnboardingTwoFragment : BaseFragment(), TitleListener {

    override fun getViewIdResource() = R.layout.fragment_onboarding_two
    private var listener: PagerListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnOk.setOnClickListener {
            listener?.onStartLogin()
        }
    }

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
