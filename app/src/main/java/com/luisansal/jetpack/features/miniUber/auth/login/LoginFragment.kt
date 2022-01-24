package com.luisansal.jetpack.features.miniUber.auth.login

import com.luisansal.jetpack.core.base.BaseBindingFragment
import com.luisansal.jetpack.databinding.FragmentMiniuberLoginBinding

class LoginFragment : BaseBindingFragment() {
    private val binding by lazy {
        FragmentMiniuberLoginBinding.inflate(layoutInflater).apply { lifecycleOwner = this@LoginFragment }
    }

    override fun getViewResource() = binding.root
}