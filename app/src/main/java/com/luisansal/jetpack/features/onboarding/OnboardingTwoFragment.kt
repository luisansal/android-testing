package com.luisansal.jetpack.features.onboarding

import android.content.Context
import android.os.Bundle
import android.view.View
import com.luisansal.jetpack.R
import com.luisansal.jetpack.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_onboarding_two.*


class OnboardingTwoFragment : BaseFragment() {

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
        listener = (context as PagerListener)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
