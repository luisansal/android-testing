package com.luisansal.jetpack.features.miniUber.auth.new

import android.view.View
import com.luisansal.jetpack.core.base.BaseBindingFragment
import com.luisansal.jetpack.databinding.FragmentMinuberAuthNewuserBinding

class NewUserFragment : BaseBindingFragment() {

    private val binding by lazy {
        FragmentMinuberAuthNewuserBinding.inflate(layoutInflater).apply { lifecycleOwner = this@NewUserFragment }
    }

    override fun getViewResource() = binding.root
}